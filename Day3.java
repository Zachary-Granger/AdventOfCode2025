import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Day3 {
	private static boolean logEnabled = false;
	private static String fileName = "input.txt";
	private static int totalVoltage = 0;

	public static void main(String[] args) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> addLineVoltage(line));
		} catch (IOException ex) {
			LOG(ex.getMessage());
		}

		LOG("---------- RESULT ------------");
		System.out.println(totalVoltage);
	}

	private static void addLineVoltage(String line) {
		LOG("----------------");
		LOG("Line: " + line);
		// Iterate over the numbers. If the digit is bigger than the first one, update it and reset the second one to the next digit in line
		// Otherwise, if the digit is bigger than the second one, update it
		int firstDigit = 0;
		int secondDigit = 0;
		for (int i = 0; i < line.length(); i++) {
			int currDigit = line.charAt(i) - '0';
			LOG("current digit: " + currDigit);
			if (i < line.length() - 1 && currDigit > firstDigit) {
				firstDigit = currDigit;
				int nextDigit = line.charAt(i+1) - '0';
				secondDigit = nextDigit;
			} else if (currDigit > secondDigit) {
				secondDigit = currDigit;
			}
		}

		int voltage = (firstDigit * 10) + secondDigit;
		LOG("Voltage for line: " + voltage);

		totalVoltage += voltage;
		LOG("Total Voltage: " + totalVoltage);
	}

	private static void LOG(String entry) {
		if (logEnabled) {
			System.out.println(entry);
		}
	}

	private static void addLineVoltage_part2(String line) {

	}
}
