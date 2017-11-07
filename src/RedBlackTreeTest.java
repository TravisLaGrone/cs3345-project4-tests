import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
//import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
//import org.junit.rules.Timeout;

@TestInstance(Lifecycle.PER_CLASS)
class RedBlackTreeTest {
	
	static final Duration TIMEOUT = Duration.ofSeconds(2);  // maybe make this shorter in practice
	
	RedBlackTree<Integer> tree;
	
//	boolean timeout_flag;
	
//	@Rule
//    public Timeout globalTimeout = Timeout.seconds(2); // 2 seconds max per method tested
//	// this annotation seems to be ignored in junit5
	
	@BeforeEach
	void setUp()
	{
		assertTimeoutPreemptively(TIMEOUT, () -> {
			
		tree = new RedBlackTree<>();		
		
		});
	}
	
//	@Test
//    void defendAgainstInfiniteLoops()
//    {        
//        assertTimeoutPreemptively(TIMEOUT, () -> {  // perform student-defined operations inside this assertion to defend against any infinite loops
//        		timeout_flag = true;
//                tree.insert(10);
//                tree.insert(85);
//                tree.insert(15);
//                tree.insert(70);
//                tree.insert(20);
//                tree.insert(60);
//                tree.insert(30);
//                tree.insert(50);
//                tree.insert(65);
//                tree.insert(80);
//                tree.insert(90);
//                tree.insert(40);
//                tree.insert(5);
//                tree.insert(55); 
//                timeout_flag = false;
//        }, 
//        		getComment(
//						"test timeout",
//						"Procedure: ",
//						String.valueOf(""),
//						String.valueOf(""),
//						50
//						)
//        );      
//    }
	
//	@Test
//    public void testSleepForTooLong() throws Exception {
//        TimeUnit.SECONDS.sleep(3); // sleep for 3 seconds
//    }	// rule annotation doesn't take effect
	
//	@Test
//	void exitIfTimeout() {
//	    assumeTrue(timeout_flag);
//	    System.exit(1);
//	}
	
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
			
			});
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
			
			});
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
			
			});
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
			
			});
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
			
			});
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
			
			});
		}
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigTest
	{
		final List<Operation<Integer>> ZIG_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
    			Stream.of
    			(
    					Operation.INSERT(10),
    					Operation.INSERT(85)    					
    			)
    			.collect(Collectors.toList())
		);
		
		@BeforeEach
		void setUp()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			});
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
			
			});
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
			
			});
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
			
			});
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
			
			});
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZigRedSiblingTest
	{
		final List<Operation<Integer>> ZIG_ZIG_RED_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
    			Stream.of
    			(
    					Operation.INSERT(10),
    					Operation.INSERT(85),
    					Operation.INSERT(15),
    					Operation.INSERT(90)
    			)
    			.collect(Collectors.toList())
    	);
		
		final String caseName = "zig-zig rotation with red parent sibling";
		
		@BeforeEach
		void setUp()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZIG_RED_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			});
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
			});
			
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
			
			});
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZagRedSiblingTest
	{
		final List<Operation<Integer>> ZIG_ZAG_RED_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
    			Stream.of
    			(
    					Operation.INSERT(10),
    					Operation.INSERT(85),
    					Operation.INSERT(15),
    					Operation.INSERT(70)
    			)
    			.collect(Collectors.toList())
    	);
		
		final String caseName = "zig-zag rotation with red parent sibling";
		
		@BeforeEach
		void setUp()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZAG_RED_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			});
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
			
			});
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
			
			});
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZigBlackSiblingTest
	{
		final List<Operation<Integer>> ZIG_ZIG_BLACK_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
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
		
		final String caseName = "zig-zig rotation with black parent sibling";
		
		@BeforeEach
		void setUp()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZIG_BLACK_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			});
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
			
			});
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
			
			});
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZagBlackSiblingTest
	{
		final List<Operation<Integer>> ZIG_ZAG_BLACK_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
    			Stream.of
    			(
    					Operation.INSERT(10),
    					Operation.INSERT(85),
    					Operation.INSERT(15)
    			)
    			.collect(Collectors.toList())
		);
		
		final String caseName = "zig-zag rotation with black parent sibling";
		
		@BeforeEach
		void setUp()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZAG_BLACK_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			});
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
			
			});
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
			
			});
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZigRedSiblingRecursiveTest
	{
		final List<Operation<Integer>> ZIG_ZIG_RED_RECURSIVE_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
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
		
		final String caseName = "zig-zig rotation with red parent sibling, recursively";
		
		@BeforeEach
		void setUp()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZIG_RED_RECURSIVE_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			});
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
			
			});
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
			
			});
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class ZigZagRedSiblingRecursiveTest
	{
		final List<Operation<Integer>> ZIG_ZAG_RED_RECURSIVE_PROCEDURE = Collections.unmodifiableList(  // unmodifiable for defensive programming purposes
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
		
		final String caseName = "zig-zag rotation with RED parent sibling, recursively";
		
		@BeforeEach
		void setUp()
		{
			assertTimeoutPreemptively(TIMEOUT, () -> {
				
			ZIG_ZAG_RED_RECURSIVE_PROCEDURE.forEach(op -> op.executeWith(tree));
			
			});
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
			
			});
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
			
			});
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
