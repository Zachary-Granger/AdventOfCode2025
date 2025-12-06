import java.awt.Adjustable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Day1 {
	private static boolean logEnabled = false;
	private static String fileName = "input.txt";
	private static int endResult = 0;
	private static int currentPosition = 50;

	public static void main(String[] args) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> moveDial(line));
		} catch (IOException ex) {
			LOG(ex.getMessage());
		}

		System.out.println(endResult);
	}

	private static void moveDial(String line){
		char direction = line.charAt(0);
		int previousPosition = currentPosition;
		int move = Integer.parseInt(line.substring(1));
		if (direction == 'R') {
			currentPosition += move;
		} else {
			currentPosition -= move;
		}

		LOG("--------------");
		LOG("" + endResult);
		LOG(previousPosition + " -> " + currentPosition);

		// Check if one was negative and the other positive. If so, 0 was passed.
		if (previousPosition * currentPosition < 0) {
			endResult++;
		}

		if (currentPosition == 0) {
			endResult++;
		} else {
			endResult += Math.floorDiv(Math.abs(currentPosition), 100);
		}

		currentPosition = Math.floorMod(currentPosition, 100);

		LOG("---");
		LOG("" + endResult);
		LOG("" + currentPosition);
		LOG("--------------");
	}

	private static void LOG(String entry) {
		if (logEnabled) {
			System.out.println(entry);
		}
	}
}
