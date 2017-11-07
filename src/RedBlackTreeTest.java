import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import junit.framework.AssertionFailedError;

@TestInstance(Lifecycle.PER_CLASS)
class RedBlackTreeTest {
	
	static final Duration TIMEOUT = Duration.ofMillis(500);
	
	RedBlackTree<Integer> tree;
	
	@Test
	void testNew()
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
		tree = new RedBlackTree<>();		
		
		}, "Timeout... suspected infinite loop");
	}
	
	@BeforeEach
	void setUp() throws AssertionFailedError
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
		tree = new RedBlackTree<>();		
		
		}, "Timeout... suspected infinite loop");
	}
	
	
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class InputNullTest
	{		
		final Class<NullPointerException> NPE = NullPointerException.class;
		final Integer NULL_KEY = null;
		
		@Test
		void testInsertNull()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			String expected = "NullPointerException";
			String actual = "";
			int points = 1;
			assertAll("Insert null test",
					() -> assertAll(
							() -> assertThrows(NPE, () -> tree.insert(NULL_KEY), 
									getComment(
											"test insert null",
											"Procedure: insert(" + NULL_KEY + ")",
											String.valueOf(expected),
											String.valueOf(actual),
											points
											)
									)						
					)
			);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testContainsNull()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			boolean expected = false;
			boolean actual = tree.contains(NULL_KEY);
			int points = 1;
			assertAll("Contains null test",
					() -> assertFalse(actual,
							getComment(
									"test contains null",
									"Procedure: contains(" + NULL_KEY + ")",
									String.valueOf(expected),
									String.valueOf(actual),
									points						
									)
							)					
			);
			
			}, "Timeout... suspected infinite loop");
		}
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class EmptyTreeTest
	{
		static final int EMPTY_KEY = 10;
		
		@Test
		void testInsert()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			boolean expected = true;
			boolean actual = tree.insert(EMPTY_KEY);
			int points = 1;
			assertAll("Insert result",
					() -> assertTrue(actual,
							getComment(
									"test insert on empty tree",
									"Procedure: insert(" + EMPTY_KEY + ")",
									String.valueOf(expected),
									String.valueOf(actual),
									points						
									)
							)
			);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testContains()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			boolean expected = false;
			boolean actual = tree.contains(EMPTY_KEY);
			int points = 1;
			assertAll("Contains result",
					() -> assertFalse(actual, 
							getComment(
									"test contains on empty tree",
									"Procedure: contains(" + EMPTY_KEY + ")",
									String.valueOf(expected),
									String.valueOf(actual),
									points						
									)
							)
			);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testToStringEmpty()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			String expected = "";
			String actual = normalizedOutput(tree.toString());
			int points = 1;
			assertEquals(
					getComment(
							"test toString on empty tree",
							"Procedure: toString()",
							String.valueOf(expected),
							String.valueOf(actual),
							points						
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testToStringSingle()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			tree.insert(EMPTY_KEY);
			String expected = "10";
			String actual = normalizedOutput(tree.toString());
			int points = 1;			
			assertEquals(
					getComment(
							"test insert single node",
							"Procedure: insert(" + EMPTY_KEY + "), toString()",
							String.valueOf(expected),
							String.valueOf(actual),
							points						
							),
					expected,
					actual
					);	
			
			}, "Timeout... suspected infinite loop");
		}
	}

	
	
	static final List<Operation<Integer>> ZIG_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
			Stream.of
			(
					Operation.INSERT(10),
					Operation.INSERT(85)    					
			)
			.collect(Collectors.toList())
	);
	
	@Test
	void testZigInsert()
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
		ZIG_PROCEDURE.forEach(op -> op.executeWith(tree));
		
		}, getComment("testZigInsert", ZIG_PROCEDURE, "", "", 0));
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigTest
	{
		
		@BeforeEach
		void setUp() throws AssertionFailedError
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			}, "Timeout... suspected infinite loop");
		}
			
		@Test
		void testContainsZig1()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			int points = 1;
			boolean actual = tree.contains(85);
			boolean expected = true;
			
			assertEquals(
					getComment(
							"test contains after inert fro zig case",
							ZIG_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testContainsZig2()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			int points = 1;
			boolean actual = tree.contains(65);
			boolean expected = false;
			
			assertEquals(
					getComment(
							"test contains after inert fro zig case",
							ZIG_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testContainsZig3()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			int points = 1;
			boolean actual = tree.contains(90);
			boolean expected = false;
			
			assertEquals(
					getComment(
							"test contains after inert fro zig case",
							ZIG_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testToStringZig()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			String expected = "10\t*85";
			String actual = normalizedOutput(tree.toString());
			int points = 3;

			assertEquals(
					getComment(
							"test toString after inert fro zig case",
							ZIG_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
	}

	
	
	static final List<Operation<Integer>> ZIG_ZIG_RED_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
			Stream.of
			(
					Operation.INSERT(10),
					Operation.INSERT(85),
					Operation.INSERT(15),
					Operation.INSERT(90)
			)
			.collect(Collectors.toList())
	);
	
	@Test
	void testZigZigRedSiblingInsert()
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
			ZIG_ZIG_RED_PROCEDURE.forEach(op -> op.executeWith(tree));
		
		}, getComment("testZigZigRedSiblingInsert", ZIG_ZIG_RED_PROCEDURE, "", "", 0));
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZigRedSiblingTest
	{
		
		final String caseName = "zig-zig rotation with red parent sibling";
		
		@BeforeEach
		void setUp() throws AssertionFailedError
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZIG_RED_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			}, "Timeout... suspected infinite loop");
		}
			
		@Test
		void testContainsZigZigRed()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			int points = 1;
			boolean actual = tree.contains(10);
			boolean expected = true;
			
			assertEquals(
					getComment(
							getCase("contains", caseName),
							ZIG_ZIG_RED_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			}, "Timeout... suspected infinite loop");
			
		}
		
		@Test
		void testToStringZigZigRed()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			String expected = String.join("\t", "10", "15", "85", "*90");
			String actual = normalizedOutput(tree.toString());
			int points = 5;

			assertEquals(
					getComment(
							getCase("toString", caseName),
							ZIG_ZIG_RED_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
	}

	
	
	static final List<Operation<Integer>> ZIG_ZAG_RED_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
			Stream.of
			(
					Operation.INSERT(10),
					Operation.INSERT(85),
					Operation.INSERT(15),
					Operation.INSERT(70)
			)
			.collect(Collectors.toList())
	);
	
	@Test
	void testZigZagRedSiblingInsert()
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
			ZIG_ZAG_RED_PROCEDURE.forEach(op -> op.executeWith(tree));
		
		}, getComment("testZigZagRedSiblingInsert", ZIG_ZAG_RED_PROCEDURE, "", "", 0));
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZagRedSiblingTest
	{
		
		final String caseName = "zig-zag rotation with red parent sibling";
		
		@BeforeEach
		void setUp() throws AssertionFailedError
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZAG_RED_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			}, "Timeout... suspected infinite loop");
		}
			
		@Test
		void testContainsZigZagRed()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			int points = 1;
			boolean actual = tree.contains(10);
			boolean expected = true;
			
			assertEquals(
					getComment(
							getCase("contains", caseName),
							ZIG_ZAG_RED_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testToStringZigZagRed()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			String expected = String.join("\t", "10", "15", "*70", "85");
			String actual = normalizedOutput(tree.toString());
			int points = 5;

			assertEquals(
					getComment(
							getCase("toString", caseName),
							ZIG_ZAG_RED_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
	}

	
	
	static final List<Operation<Integer>> ZIG_ZIG_BLACK_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
			Stream.of
			(
					Operation.INSERT(10),
					Operation.INSERT(85),
					Operation.INSERT(15),
					Operation.INSERT(70),
					Operation.INSERT(20)
			)
			.collect(Collectors.toList())
	);
	
	@Test
	void testZigZigBlackSiblingInsert()
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
			ZIG_ZIG_BLACK_PROCEDURE.forEach(op -> op.executeWith(tree));
		
		}, getComment("testZigZigBlackSiblingInsert", ZIG_ZIG_BLACK_PROCEDURE, "", "", 0));
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZigBlackSiblingTest
	{
		
		final String caseName = "zig-zig rotation with black parent sibling";
		
		@BeforeEach
		void setUp() throws AssertionFailedError
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZIG_BLACK_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			}, "Timeout... suspected infinite loop");
		}
			
		@Test
		void testContainsZigZigBlack()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			int points = 1;
			boolean actual = tree.contains(85);
			boolean expected = true;
			
			assertEquals(
					getComment(
							getCase("contains", caseName),
							ZIG_ZIG_BLACK_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testToStringZigZigBlack()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			String expected = String.join("\t", "10", "15", "*20", "70", "*85");
			String actual = normalizedOutput(tree.toString());
			int points = 3;

			assertEquals(
					getComment(
							getCase("toString", caseName),
							ZIG_ZIG_BLACK_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
	}

	
	
	static final List<Operation<Integer>> ZIG_ZAG_BLACK_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
			Stream.of
			(
					Operation.INSERT(10),
					Operation.INSERT(85),
					Operation.INSERT(15)
			)
			.collect(Collectors.toList())
	);
	
	@Test
	void testZigZagBlackSiblingInsert()
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
			ZIG_ZAG_BLACK_PROCEDURE.forEach(op -> op.executeWith(tree));
		
		}, getComment("testZigZagBlackSiblingInsert", ZIG_ZAG_BLACK_PROCEDURE, "", "", 0));
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZagBlackSiblingTest
	{
		
		final String caseName = "zig-zag rotation with black parent sibling";
		
		@BeforeEach
		void setUp() throws AssertionFailedError
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZAG_BLACK_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			}, "Timeout... suspected infinite loop");
		}
			
		@Test
		void testContainsZigZagBlack()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			int points = 1;
			boolean actual = tree.contains(18);
			boolean expected = false;
			
			assertEquals(
					getComment(
							getCase("contains", caseName),
							ZIG_ZAG_BLACK_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testToStringZigZagBlack()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			String expected = String.join("\t", "*10", "15", "*85");
			String actual = normalizedOutput(tree.toString());
			int points = 3;

			assertEquals(
					getComment(
							getCase("toString", caseName),
							ZIG_ZAG_BLACK_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
	}

	
	
	static final List<Operation<Integer>> ZIG_ZIG_RED_RECURSIVE_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
			Stream.of
			(
					Operation.INSERT(10),
					Operation.INSERT(85),
					Operation.INSERT(15),
					Operation.INSERT(70),
					Operation.INSERT(20),
					Operation.INSERT(60),
					Operation.INSERT(30),
					Operation.INSERT(50),
					Operation.INSERT(65),
					Operation.INSERT(80),
					Operation.INSERT(90),
					Operation.INSERT(40)
			)
			.collect(Collectors.toList())
	);
	
	@Test
	void testZigZigRedSiblingRecursiveInsert()
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
			ZIG_ZIG_RED_RECURSIVE_PROCEDURE.forEach(op -> op.executeWith(tree));
		
		}, getComment("testZigZigRedSiblingRecursiveInsert", ZIG_ZIG_RED_RECURSIVE_PROCEDURE, "", "", 0));
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZigRedSiblingRecursiveTest
	{
		
		final String caseName = "zig-zig rotation with red parent sibling, recursively";
		
		@BeforeEach
		void setUp() throws AssertionFailedError
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZIG_RED_RECURSIVE_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			}, "Timeout... suspected infinite loop");
		}
			
		@Test
		void testContainsZigZigRedRecursive()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			int points = 1;
			boolean actual = tree.contains(60);
			boolean expected = true;
			
			assertEquals(
					getComment(
							getCase("contains", caseName),
							ZIG_ZIG_RED_RECURSIVE_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testToStringZigZigRedRecursive()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			String expected = String.join("\t", "10", "15", "20", "30", "*40", "50", "*60", "65", "70", "*80", "85", "*90");
			String actual = normalizedOutput(tree.toString());
			int points = 4;

			assertEquals(
					getComment(
							getCase("toString", caseName),
							ZIG_ZIG_RED_RECURSIVE_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
	}

	
	
	static final List<Operation<Integer>> ZIG_ZAG_RED_RECURSIVE_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
			Stream.of
			(
					Operation.INSERT(10),
					Operation.INSERT(85),
					Operation.INSERT(15),
					Operation.INSERT(70),
					Operation.INSERT(20),
					Operation.INSERT(60),
					Operation.INSERT(30),
					Operation.INSERT(50)
			)
			.collect(Collectors.toList())
	);
	
	@Test
	void testZigZagRedSiblingRecursiveInsert()
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
			ZIG_ZAG_RED_RECURSIVE_PROCEDURE.forEach(op -> op.executeWith(tree));
		
		}, getComment("testZigZagRedSiblingRecursiveInsert", ZIG_ZAG_RED_RECURSIVE_PROCEDURE, "", "", 0));
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZagRedSiblingRecursiveTest
	{
		
		final String caseName = "zig-zag rotation with RED parent sibling, recursively";
		
		@BeforeEach
		void setUp() throws AssertionFailedError
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZAG_RED_RECURSIVE_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			}, "Timeout... suspected infinite loop");
		}
			
		@Test
		void testContainsZigZagRedRecursive()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			int points = 1;
			boolean actual = tree.contains(15);
			boolean expected = true;
			
			assertEquals(
					getComment(
							getCase("contains", caseName),
							ZIG_ZAG_RED_RECURSIVE_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
		@Test
		void testToStringZigZagRedRecursive()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			String expected = String.join("\t", "10", "*15", "20", "30", "*50", "60", "*70","85");
			String actual = normalizedOutput(tree.toString());
			int points = 4;

			assertEquals(
					getComment(
							getCase("toString", caseName),
							ZIG_ZAG_RED_RECURSIVE_PROCEDURE,
							String.valueOf(expected),
							String.valueOf(actual),
							points
							),
					expected,
					actual
					);
			
			}, "Timeout... suspected infinite loop");
		}
		
	}

	
	
	///////////////
	// UTILITIES //
	///////////////
	
	String getCase(String opName, String caseName) {
		return String.join("", "test of ", opName, " for case of ", caseName);
	}
	
	String getComment(String testCase, List<Operation<Integer>> procedure, String expected, String actual, int points) {
		return getComment(testCase, ProcedureString.from(procedure), expected, actual, points);
	}
	
	String getComment(String testCase, String procedure, String expected, String actual, int points) {
		return "\n+++++++++++++++++++++\n" +
				"Failure: " + testCase + ".\n" +
				procedure + 
				"\nExpected: " + expected + 
				"\nActual: " + actual + 					
				"\n(-" + points + ")" +
				"\n--------------------\n";
	}
	
	String normalizedOutput(String s)
	{
		return s.replaceAll("[\\s\\t]*\\*", "\t*")  // ensure exactly one tab preceding any asterisk
				.replaceAll("\\*[\\s\\t]+", "*")  // ensure no whitespace or tab immediately following any asterisk
				.trim();  // remove leading and trailing whitespace
	}
	
	static class ProcedureString
    {
    		static final String PROCEDURE_STRING_HEADER = "Procedure: ";
        static final String PROCEDURE_STRING_SEPARATOR = ", ";

        static <T extends Comparable<T>> String from(List<Operation<T>> procedure)
        {
            return ProcedureString.from( procedure.stream() );
        }

        // convenience method
        static <T extends Comparable<T>> String from(List<Operation<T>> procedure,
        		Operation<T> anotherOperation)
        {
            return ProcedureString.from( Stream.concat(procedure.stream(), Stream.of(anotherOperation)) );
        }
        
        // convenience method
        @SafeVarargs
        static <T extends Comparable<T>> String from(List<Operation<T>> procedure,
        		Operation<T> ...moreOperations)
        {
        		return ProcedureString.from( Stream.concat(procedure.stream(), Arrays.stream(moreOperations)) );
        }
        
        // extracted to allow for convenience methods without sacrificing DRY logic
        static <T extends Comparable<T>> String from(Stream<Operation<T>> procedureStream)
        {
            return PROCEDURE_STRING_HEADER + procedureStream
                    .map(op -> op.toString())
                    .collect(Collectors.joining(PROCEDURE_STRING_SEPARATOR));
        }
        
    }
    
    static class Operation <T extends Comparable<T>>  // "extends Comparable<T>" is only needed because that's what the RedBlackTree uses
    {
    		static final String ARGUMENTS_PREFIX = "(";
    		static final String ARGUMENT_SEPARATOR = ",";
    		static final String ARGUMENTS_SUFFIX = ")";
    		
    		static enum Method { contains, toString, insert };
    	
        private final Method method;
        private final List<T> arguments;
        private final BiConsumer<RedBlackTree<T>,List<T>> lambda;

        // this constructor should never be invoked directly; use the static factory methods below
        private Operation(Method method, List<T> arguments, BiConsumer<RedBlackTree<T>,List<T>> lambda)
        {
            this.method = method;
            this.arguments = arguments;
            this.lambda = lambda;
        }

        static <T extends Comparable<T>> Operation<T> CONTAINS(T value)
        {
            return new Operation<>(
            			Method.contains,
            			Collections.singletonList(value),
            			(ds,args) -> { ds.contains(args.get(0)); }  // this should never be called since "contains" is not needed for setup procedures
            	);
        }

        static <T extends Comparable<T>> Operation<T> TOSTRING(T value)
        {
            return new Operation<>(
            			Method.toString,
            			Collections.singletonList(value),
            			(ds,args) -> { ds.toString(); }
            	);
        }

        static <T extends Comparable<T>> Operation<T> INSERT(T value)
        {
            return new Operation<>(
            			Method.insert,
            			Collections.singletonList(value),
            			(ds,args) -> { ds.insert(args.get(0)); }
            	);
        }

        /* No return value since assumed Operation only executed generically in setup procedures.
         * (otherwise, just invoke the student data structure's actual method directly!)
         */
        void executeWith(RedBlackTree<T> RedBlackTree)
        {
            lambda.accept(RedBlackTree, arguments);
        }

        @Override
        public String toString()
        {
        		StringJoiner argsJoiner = new StringJoiner(ARGUMENT_SEPARATOR, ARGUMENTS_PREFIX, ARGUMENTS_SUFFIX);
        		arguments.forEach(arg -> argsJoiner.add(Objects.toString(arg)));  // use Objects to defend against null (e.g. if null for input validation)
            return method.toString() + argsJoiner.toString();  // enums automatically convert to their exact value name
        }
        
    }

}
