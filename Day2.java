import java.awt.Adjustable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Day2 {
	private static boolean logEnabled = false;
	private static String fileName = "input.txt";

	public static void main(String[] args) {
		List<Long> invalidIds = new ArrayList<>();
		// invalidIds.addAll(getInvalidIdsForRange(1211, 1213));

		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Split the line by comma
				String[] values = line.split(",");

				// Process the values
				for (String value : values) {
					String[] minAndMax = value.split("-");
					invalidIds.addAll(getInvalidIdsForRange(Long.parseLong(minAndMax[0]), Long.parseLong(minAndMax[1])));
				}
			}
		} catch (IOException e) {
			LOG(e.getMessage());
		}

		System.out.println(addUpInvalidIds(invalidIds));
	}

	private static List<Long> getInvalidIdsForRange(long min, long max) {
		List<Long> invalidIdsInRange = new ArrayList<>();
		for(long i = min; i <= max; i++) {
			LOG("-----------------");
			LOG("Trying number: " + i);
			if (tryAllLengths(i)) {
				invalidIdsInRange.add(i);
				LOG("FOUND MATCH: " + i);
			}
		}

		return invalidIdsInRange;
	}

	private static boolean tryAllLengths(long number) {
		int digitCount = (int) (Math.log10(number) + 1);
		LOG("Number of digits: " + digitCount);

		int dividor = 2;

		// iterate through each dividor option until we're inspecting individual digits
		while (dividor <= digitCount) {
			boolean foundMatch = true;

			// Is the number of digits evenly divisible into portions?
			if (digitCount % dividor == 0) {
				int splitDigitCount = digitCount / dividor;
				LOG("Splitting " + number + " into chunks of " + splitDigitCount);

				// split the number into chunks of the splitDigitCount and check that they're all equal
				long firstSection = number % (long) Math.pow(10, splitDigitCount);
				int sectionModulo = 2;
				LOG("First section: " + firstSection);

				// Go through each section one at a time and see if there's a match. If not, skip to the next dividor loop.
				// If it gets through the whole loop, then we have a match.
				while (sectionModulo * splitDigitCount <= digitCount) {
					long newSection = number % (long) Math.pow(10, splitDigitCount * sectionModulo) / (long) Math.pow(10, splitDigitCount * (sectionModulo - 1));
					LOG("new section: " + newSection);
					if (newSection != firstSection) {
						foundMatch = false;
						break;
					}
					sectionModulo++;
				}
			} else {
				foundMatch = false;
			}

			if (foundMatch) return true;
			dividor++;
		}

		return false;
	}

	private static long addUpInvalidIds(List<Long> invalidIds) {
		return invalidIds.stream().mapToLong(Long::longValue).sum();
	}

	private static void LOG(String entry) {
		if (logEnabled) {
			System.out.println(entry);
		}
	}
}
