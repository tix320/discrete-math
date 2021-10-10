package discretemath.bool.minimization;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import discretemath.bool.BooleanTable;
import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.atomic.BooleanVariable;
import discretemath.bool.expression.compound.CompoundBooleanExpression;
import discretemath.bool.expression.compound.ExpressionTreeNode;
import discretemath.bool.expression.compound.OperatorNode;
import discretemath.bool.expression.compound.ValueNode;
import discretemath.bool.expression.exception.InvalidArgumentCountException;
import discretemath.bool.expression.forms.Minterm;
import discretemath.bool.expression.forms.SumOfProductExpression;
import discretemath.bool.operator.Operators;
import discretemath.bool.table.FunctionValue;
import discretemath.bool.table.TruthTable;
import discretemath.bool.table.TruthTableRow;
import discretemath.common.BitString;
import discretemath.common.MoreThanOneBitDifferenceException;
import discretemath.utils.ArrayUtils;

public final class McCluskey {

	public McCluskey() {
	}

	public BooleanExpression minimizeFromTruthTable(TruthTable truthTable, List<BooleanVariable> variables) {
		InvalidArgumentCountException.checkExact(1, truthTable.getFunctionsCount());
		InvalidArgumentCountException.checkExact(variables.size(), truthTable.getVariablesCount());

		int variablesCount = truthTable.getVariablesCount();

		List<List<LinkedHashSet<Implicant>>> implicantsTable = initImplicantsTable(variablesCount);

		List<Long> mintermNumbers = new ArrayList<>();

		for (TruthTableRow truthTableRow : truthTable) {
			FunctionValue functionValue = truthTableRow.getFunctionValue(0);

			if (functionValue == FunctionValue.FALSE) {
				continue;
			}

			BitString bitString = truthTableRow.getArgumentsBitString();
			int bitCount = bitString.bitCount();

			List<LinkedHashSet<Implicant>> nBitImplicantsList = implicantsTable.get(bitCount);

			long mintermNumber = bitString.asLong();

			if (functionValue == FunctionValue.TRUE) {
				mintermNumbers.add(mintermNumber);
			}

			nBitImplicantsList.get(0)
					.add(new Implicant(bitString, Set.of(), Set.of(mintermNumber),
							functionValue == FunctionValue.DONT_CARE));
		}

		return apply(variablesCount, mintermNumbers, implicantsTable, variables);
	}

	public BooleanExpression minimizeSOPExpression(SumOfProductExpression expression) {
		List<BitString> bitStrings = expression.getMinterms().stream().map(Minterm::toBitString).toList();

		int variablesCount = expression.getVariables().size();

		List<List<LinkedHashSet<Implicant>>> implicantsTable = initImplicantsTable(variablesCount);

		List<Long> mintermNumbers = new ArrayList<>(bitStrings.size());

		for (BitString bitString : bitStrings) {
			int bitCount = bitString.bitCount();

			List<LinkedHashSet<Implicant>> nBitImplicantsList = implicantsTable.get(bitCount);

			long mintermNumber = bitString.asLong();

			mintermNumbers.add(mintermNumber);

			nBitImplicantsList.get(0).add(new Implicant(bitString, Set.of(), Set.of(mintermNumber), false));
		}


		return apply(variablesCount, mintermNumbers, implicantsTable, expression.getVariables());

	}

	private static CompoundBooleanExpression apply(int variablesCount, List<Long> mintermNumbers,
												   List<List<LinkedHashSet<Implicant>>> implicantsTable,
												   List<BooleanVariable> variables) {
		List<Implicant> primeImplicants = new ArrayList<>();

		for (int depthIndex = 0; depthIndex < variablesCount; depthIndex++) {
			Set<Implicant> used = new HashSet<>();

			for (int i = 0; i < implicantsTable.size() - 1; i++) {
				List<LinkedHashSet<Implicant>> group1 = implicantsTable.get(i);
				List<LinkedHashSet<Implicant>> group2 = implicantsTable.get(i + 1);

				LinkedHashSet<Implicant> implicants1 = group1.get(depthIndex);
				LinkedHashSet<Implicant> implicants2 = group2.get(depthIndex);

				for (Implicant implicant1 : implicants1) {

					for (Implicant implicant2 : implicants2) {
						try {
							int differBitIndex = getImplicantsDifferBitIndex(implicant1, implicant2);
							HashSet<Integer> dashesIndexes = new HashSet<>(implicant1.dashIndexes());
							dashesIndexes.add(differBitIndex);

							Set<Long> newMintermNumbers = Stream.concat(implicant1.mintermNumbers().stream(),
									implicant2.mintermNumbers().stream()).collect(Collectors.toSet());

							Implicant newImplicant = new Implicant(implicant1.bitString(), dashesIndexes,
									newMintermNumbers, implicant1.dontCare() && implicant2.dontCare());

							group1.get(depthIndex + 1).add(newImplicant);

							used.add(implicant1);
							used.add(implicant2);
						} catch (MoreThanOneBitDifferenceException ignored) {

						}
					}
				}
			}

			for (List<LinkedHashSet<Implicant>> group : implicantsTable) {
				LinkedHashSet<Implicant> implicants = group.get(depthIndex);
				for (Implicant implicant : implicants) {
					if (!used.contains(implicant) && !implicant.dontCare()) {
						primeImplicants.add(implicant);
					}
				}
			}
		}

		BooleanTable primeImplicantsChart = BooleanTable.create(primeImplicants.size(), mintermNumbers.size());

		Map<Long, Integer> mintermNumbersToIndexes = new HashMap<>();
		for (int i = 0; i < mintermNumbers.size(); i++) {
			Long mintermNumber = mintermNumbers.get(i);
			mintermNumbersToIndexes.put(mintermNumber, i);
		}

		for (int i = 0; i < primeImplicants.size(); i++) {
			Implicant primeImplicant = primeImplicants.get(i);
			for (Long mintermNumber : primeImplicant.mintermNumbers()) {
				Integer columnIndex = mintermNumbersToIndexes.get(mintermNumber);
				if (columnIndex == null) {
					continue; // don't care number
				}
				primeImplicantsChart.set(i, columnIndex, true);
			}
		}

		int[] rowsCoverage = new int[primeImplicantsChart.rowsCount()];

		for (int i = 0; i < primeImplicantsChart.rowsCount(); i++) {
			for (int j = 0; j < primeImplicantsChart.columnsCount(); j++) {
				boolean cross = primeImplicantsChart.get(i, j);
				if (cross) {
					rowsCoverage[i]++;
				}
			}
		}

		int coveredColumnsCount = 0;

		List<Implicant> resultImplicants = new ArrayList<>();

		while (coveredColumnsCount != primeImplicantsChart.columnsCount()) {
			List<Integer> maxCoveredRowsIndexes = ArrayUtils.getIndexesOfMax(rowsCoverage);

			int maxCoveredRowIndex;

			if (maxCoveredRowsIndexes.size() == 1) {
				maxCoveredRowIndex = maxCoveredRowsIndexes.get(0);
			} else {
				int maxMintermsCountIndex = maxCoveredRowsIndexes.get(0);
				for (int i = 1; i < maxCoveredRowsIndexes.size(); i++) {
					Integer index = maxCoveredRowsIndexes.get(i);
					Implicant implicant = primeImplicants.get(index);
					if (implicant.mintermNumbers().size() > primeImplicants.get(maxMintermsCountIndex)
							.mintermNumbers()
							.size()) {
						maxMintermsCountIndex = index;
					}
				}

				maxCoveredRowIndex = maxMintermsCountIndex;
			}

			assert rowsCoverage[maxCoveredRowIndex] > 0;

			resultImplicants.add(primeImplicants.get(maxCoveredRowIndex));

			for (int j = 0; j < primeImplicantsChart.columnsCount(); j++) {
				if (primeImplicantsChart.get(maxCoveredRowIndex, j)) {
					for (int i = 0; i < primeImplicantsChart.rowsCount(); i++) {
						if (primeImplicantsChart.get(i, j)) {
							primeImplicantsChart.set(i, j, false);
							rowsCoverage[i]--;
						}

					}
					coveredColumnsCount++;
				}
			}

			rowsCoverage[maxCoveredRowIndex] = 0;
		}

		return convertImplicantsSumToCompoundExpression(resultImplicants, variables);
	}

	private static List<List<LinkedHashSet<Implicant>>> initImplicantsTable(int variablesCount) {
		List<List<LinkedHashSet<Implicant>>> implicantsTable = new ArrayList<>(variablesCount + 1);

		for (int i = 0; i < variablesCount + 1; i++) {
			ArrayList<LinkedHashSet<Implicant>> nBitImplicantsList = new ArrayList<>();
			for (int j = 0; j < variablesCount; j++) {
				nBitImplicantsList.add(new LinkedHashSet<>());
			}
			implicantsTable.add(nBitImplicantsList);
		}

		return implicantsTable;
	}

	private static int getImplicantsDifferBitIndex(Implicant implicant,
												   Implicant other) throws MoreThanOneBitDifferenceException {
		if (implicant.dashIndexes().equals(other.dashIndexes())) {
			return implicant.bitString().getDifferBitIndex(other.bitString());
		} else {
			throw new MoreThanOneBitDifferenceException("Dashes");
		}
	}

	private static CompoundBooleanExpression convertImplicantsSumToCompoundExpression(List<Implicant> implicants,
																					  List<BooleanVariable> variables) {
		OperatorNode[] products = new OperatorNode[implicants.size()];

		for (int i = 0; i < implicants.size(); i++) {
			Implicant implicant = implicants.get(i);
			OperatorNode productNode = implicant.toOperatorNode(variables);
			products[i] = productNode;
		}

		return new CompoundBooleanExpression(new OperatorNode(Operators.OR, products));
	}

	public record Implicant(BitString bitString, Set<Integer> dashIndexes, Set<Long> mintermNumbers, boolean dontCare) {

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(bitString.toString());
			for (Integer dashIndex : dashIndexes) {
				builder.setCharAt(dashIndex, '-');
			}

			builder.append(" ");
			builder.append(mintermNumbers);

			return builder.toString();
		}

		public OperatorNode toOperatorNode(List<BooleanVariable> variables) {
			Iterator<BooleanVariable> variableIterator = variables.iterator();

			ExpressionTreeNode[] nodes = new ExpressionTreeNode[bitString.length() - dashIndexes.size()];

			int index = 0;
			for (int i = 0; i < bitString.length(); i++) {
				BooleanVariable variable = variableIterator.next();
				if (!dashIndexes.contains(i)) {
					boolean positive = bitString.get(i);
					ValueNode valueNode = new ValueNode(variable);
					if (positive) {
						nodes[index++] = valueNode;
					} else {
						nodes[index++] = OperatorNode.forNegation(valueNode);
					}
				}
			}

			return new OperatorNode(Operators.AND, nodes);
		}
	}
}
