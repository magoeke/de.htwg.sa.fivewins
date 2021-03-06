package de.htwg.fivewins.controller.game;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.fivewins.model.ai.AIAdapter;
import de.htwg.fivewins.model.ai.VerySillyAI;
import de.htwg.fivewins.model.field.IField;
import de.htwg.fivewins.model.field.IFieldFactory;
import de.htwg.fivewins.persistence.IFieldDAO;
import de.htwg.util.observer.Observable;

/**
 * This class is the game controller. e.g. which players turn
 */
@Singleton
public class FiveWinsController extends Observable implements
		IFiveWinsController {

	public static final int FIVEWINS = 5;
	public static final int TWO = 2;
	private static final int ASK_TIMEOUT = 5;
	private String statusMessage = "Welcome to HTWG Five Wins!";
	private IField field;
	private int turn = 0;
	private int needToWin;
	private int lastx;
	private int lasty;
	private boolean win = false;
	private String winner = null;
	// player2 possible values: pvp = null, NPC vs. Player = AI Object
	private AIAdapter player2 = null;
	private boolean draw = false;
	private IFieldFactory fieldFactory;
	private IFieldDAO database;

	private ActorRef diagonalWorker;
	private ActorRef horizontalWorker;
	private ActorRef verticalWorker;
	private ActorRef diagonalReflectedWorker;

	private static final Logger LOGGER = Logger
			.getLogger("de.htwg.fivewins.controller");

	/**
	 * initialize a Controller for a Player vs. Player game
	 */
	@Inject
	public FiveWinsController(IFieldFactory fieldFactory, IFieldDAO database) {
		this.fieldFactory = fieldFactory;
		this.field = fieldFactory.create(FIVEWINS);
		this.database = database;

		initActors();
		calculateNeedToWin();
	}

	private void initActors() {
		ActorSystem actorSystem = ActorSystem.create("FiveWins");
		diagonalWorker = actorSystem.actorOf(Props.create(Diagonal.class),
				"diagonalWorker");
		horizontalWorker = actorSystem.actorOf(Props.create(Horizontal.class),
				"horizontalWorker");
		verticalWorker = actorSystem.actorOf(Props.create(Vertical.class),
				"verticalWorker");
		diagonalReflectedWorker = actorSystem.actorOf(
				Props.create(DiagonalReflected.class),
				"diagonalReflectedWorker");
	}

	/*
	 * set the variable needToWin. set need to win 5 if the gamefield is bigger
	 * than 4x4 otherwise it set needToWin like the game field size
	 */
	private void calculateNeedToWin() {
		if (field.getSize() < FIVEWINS) {
			needToWin = field.getSize();
			// after calculate
			return;
		}
		needToWin = FIVEWINS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.game.IFiveWinsController#setValue(int,
	 * int, java.lang.String)
	 */
	public boolean setValue(int column, int row, String value) {
		// input must be right
		// calculate array position from user input
		lastx = column - 1;
		lasty = row - 1;

		String cellVal = field.getCellValue(lastx, lasty);
		boolean result = false;

		if ("-".equals(cellVal)) {
			// cell is empty
			field.setValue(lastx, lasty, value);
			setStatusMessage("The cell " + column + " " + row
					+ " was successfully set.");
			result = true;
		} else {
			// cell is alreaedy set.
			setStatusMessage("The cell " + column + " " + row
					+ " is already set.");
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getStatus()
	 */
	public String getStatus() {
		return statusMessage;
	}

	/*
	 * change the value of statusMessage
	 */
	private void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getFieldString()
	 */
	public String getFieldString() {
		return field.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getField()
	 */
	public String[][] getField() {
		return field.getGameField();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#countTurn()
	 */
	public int countTurn() {
		return turn++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getPlayerSign()
	 */
	public String getPlayerSign() {
		int result = turn % TWO;
		if (result == 0) {
			return "X";
		} else {
			return "O";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getWinner()
	 */
	public boolean getWinner() {
		return win;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getWinnerSign()
	 */
	public String getWinnerSign() {
		return winner;
	}

	// ---------- Win request start

	/*
	 * for better understanding look at the picture in the readme
	 */
	public String winRequest() {

		ArrayList<Future<Object>> futures = new ArrayList<Future<Object>>();

		Timeout timeout = new Timeout(Duration.create(ASK_TIMEOUT, "seconds"));
		Work work = new Work(lastx, lasty, 0, getPlayerSign(), true, field);

		futures.add(Patterns.ask(diagonalWorker, work, timeout));
		futures.add(Patterns.ask(horizontalWorker, work, timeout));
		futures.add(Patterns.ask(verticalWorker, work, timeout));
		futures.add(Patterns.ask(diagonalReflectedWorker, work, timeout));

		try {
			// wait for worker replies
			for (Future<Object> f : futures) {
				Result res = (Result) Await.result(f, timeout.duration());
				// return as soon as win is recognized
				if (res.getResult() + 1 == needToWin) {
					win = true;
					winner = getPlayerSign();
					return winner;
				}
			}
		} catch (Exception e) {
			LOGGER.info(e.toString());
		}

		if (isItADraw()) {
			draw = true;
			return "draw";
		}

		return "";
	}

	/*
	 * Checks if game is a draw.
	 */
	private boolean isItADraw() {
		boolean returnValue = true;
		for (int i = 0; i < field.getSize(); i++) {
			for (int j = 0; j < field.getSize(); j++) {
				if (field.getCellValue(i, j).equals("-")) {
					i = field.getSize();
					j = field.getSize();
					returnValue = false;
				}
			}
		}

		return returnValue;
	}

	// ---------- Win request end ------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#reset()
	 */
	public void reset() {
		field.reset();
		setStatusMessage("Welcome to HTWG Five Wins!");
		turn = 0;
		win = false;
		winner = null;
		draw = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getSecondPlayer()
	 */
	public AIAdapter getSecondPlayer() {
		return player2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getTurn()
	 */
	public int getTurn() {
		return turn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#getDraw()
	 */
	public boolean getDraw() {
		return draw;
	}

	/*
	 * handel inputed command reset, update or set value
	 */
	public boolean handleInputOrQuit(String line) {
		boolean quit = false;
		if (line.equalsIgnoreCase("q")) {
			quit = true;
		}
		if (line.equalsIgnoreCase("u")) {
			// Do nothing, just redraw the updated grid
			notifyObservers();
		}
		if (line.equalsIgnoreCase("n")) {
			// Restart game
			reset();
		}

		handlePlayerInput(line);

		if (!win && player2 != null
				&& player2.getWhichPlayer().equals(getPlayerSign())) {
			handlePlayerInput(player2.getCommand());
		}

		return quit;
	}

	/*
	 * Checks if player input is a correct turn. If the input is correct it
	 * calls the winrequest.
	 */
	private void handlePlayerInput(String line) {
		if (line.matches("[0-9]{1,2}?,[0-9]{1,2}?")) {
			String[] numbers = line.split(",");
			int arg0 = Integer.parseInt(numbers[0]);
			int arg1 = Integer.parseInt(numbers[1]);
			boolean successfulFieldChange = setValue(arg0, arg1,
					getPlayerSign());

			winRequest();
			isItADraw();

			if (successfulFieldChange) {
				countTurn();
			}
			notifyObservers();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#resizeGameField(int)
	 */
	public void resizeGameField(int fieldsize) {
		field = fieldFactory.create(fieldsize);
		if (player2 != null) {
			player2.updateField(field);
		}
		turn = 0;
		calculateNeedToWin();
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.IFiveWinsController#createAI()
	 */
	public void createAI(String difficulty) {
		// At the moment only the silly ai works. But it's planned that more ais
		// work.
		player2 = new VerySillyAI("O", field);
		// strategy pattern?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.game.IFiveWinsController#setTurn(int)
	 */
	@Override
	public void setTurn(int turn) {
		this.turn = turn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.htwg.fivewins.controller.game.IFiveWinsController#setField(de.htwg
	 * .fivewins.model.field.IField)
	 */
	@Override
	public void setField(IField field) {
		this.field = field;
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.game.IFiveWinsController#getAllFields()
	 */
	@Override
	public List<IField> getAllFields() {
		return database.getAllFields();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.htwg.fivewins.controller.game.IFiveWinsController#deleteAllGames()
	 */
	@Override
	public void deleteAllGames() {
		List<IField> savedGames = database.getAllFields();
		for (IField savedGame : savedGames) {
			database.deleteFieldById(savedGame.getId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.fivewins.controller.game.IFiveWinsController#saveGame()
	 */
	@Override
	public void saveGame() {
		database.saveField(field);
	}

}
