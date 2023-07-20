package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Java streams
//very similar to javascript iterators
//allow us to perform operations on a sequence of elements
//.filter
//.map
//.reduce

//before we can use any of those methods, we need to turn our data structure, 
//like Array, ArrayList to a stream

//Stream is a wrapper around a data source
//a pipeline through which data flows

//Why would we want to use streams?
//similar reasons to why we would use iterators in js
//more readable
//less chance bugs

//In Java streams could be more efficient than loops


public class Main {

	public static void main(String[] args) {
		
		Integer[] numbers = {1,2,3,4,5};
		Integer[] doubled = new Integer[numbers.length];
		
		// double the numbers with for loop
		for (int i = 0; i < numbers.length; i++) {
			doubled[i] = numbers[i] * 2;
		}
		
		// we can turn the numbers array into a stream to access to stream methods (iterators)
		Stream<Integer> numbersStream = Stream.of(numbers);

		// .map
		Stream<Integer> doubleNumbersStream = numbersStream
				.map(MathHelper::doubleNumber); // we can pass a method reference: (ClassName::MethodName)
			// .map returns a Stream -> we need a stream to store it
		
		// convert stream back to an Array
		Integer[] doubleWithStream = doubleNumbersStream.toArray(Integer[]::new);
			// Integer[]::new : reference to creating new Integet Array method
		
		
		// lambda function 
			// instead of passing method reference
			// we can set integrated function similar to as in JS iterators
				// numbers.map((num) => doubleNumber(num))
		Integer[] dNums = Stream.of(numbers)
				.map(num -> num *2)
				.toArray(Integer[]::new);
			// convert to steam -> use map -> set lambda function in map -> convert stream back to array
		
		// .filter
		Integer[] moreThanThree = Stream.of(numbers)
				.filter(num -> num > 3)
				.toArray(Integer[]::new);
		System.out.println(Arrays.toString(moreThanThree));
		
		
		// map & filter
			// double numbers that are greater than 3
		Integer[] doubledMoreThanThree = Stream.of(numbers)
				.map(MathHelper::doubleNumber)
				.filter(num -> num > 3)
				.toArray(Integer[]::new);
		System.out.println(Arrays.toString(doubledMoreThanThree));
		
		
		
		
		
		// ArrayList with Stream
		
		// generate an ArrayList and add some items to it
		ArrayList<String> names = new ArrayList<>(Arrays
				.asList("Alex", "Alice", "John", "Kate", "andy"));
		System.out.println(names);
		
		
		//// use for loop to add name starts with a to startsWithA ArrayList
		ArrayList<String> startsWithA = new ArrayList<>();
		for(String name : names) {
			if(name.toLowerCase().charAt(0) == 'a') { // single quotes for literal chars
				startsWithA.add(name);
			}
		}
		System.out.println(startsWithA);
		
		
		//// use stream to add name starts with a to startsWithAWithStream ArrayList
		ArrayList<String> startsWithAWithStream = names
				// convert ArrayList to stream
				.stream()
				
				// use .filter to find name starts with a
				.filter(n -> n.toLowerCase().charAt(0) == 'a')
				
				// use .collect to convert back to ArrayList
				.collect(Collectors
						.toCollection(ArrayList::new));
					// 
		
		System.out.println(startsWithAWithStream);
		
		
		// Optional data type
		// - A container object which may or may not contain a non-null value.
		// - If a value is present, `isPresent()` will return `true` and `get()` will return the value.
		
		// firstAName may or may not have value
		Optional<String> firstAName =  startsWithA
			.stream()
			// convert ArrayList to stream
			.filter(n -> n.toLowerCase().charAt(0) == 'a')
			// filter through startsWithA ArrayList to find names started with a
			.findFirst();
			// .findFirst() is a method of stream 
			// -> Returns an Optional describing the first element of this stream, or an empty Optional if the stream is empty.
		
		// monad
		
		if(firstAName.isPresent()) {
			System.out.println(firstAName.get());
		} else {
			System.out.println("Guest");
		}
	
		
		///// Consultant use case
		Consultant kate = new Consultant("Kate", "Melbourne");
		Consultant john = new Consultant("John", "Sydney");
	    Consultant alice = new Consultant("Alice", "Adelaide");

	    Optional<Consultant> maybeConsultant = Consultant.findById(26);

	    if (maybeConsultant.isPresent()) {
	      System.out.println(maybeConsultant.get().getName() + " found consultant");
	    } else {
	      System.out.println("Consultant not found");
	    }

	    kate.increaseSalary(5000.00);
	    alice.increaseSalary(2000.00);

	    System.out.println(Consultant.totalSalaryCost());

	    System.out.println(Consultant.calculateAverage());

	    Consultant.getConsultants().stream().forEach(Consultant::increaseBy5k);

	    Consultant
	      .getConsultants()
	      .stream()
	      .forEach(c -> System.out.println(c.toString()));
	    
	    
	    //*** reduce
	    
	    //** reduce with 2 parameters
	    ArrayList<Integer> numbers2 = new ArrayList<>(Arrays.asList(2,3,4));
	    
	    // use case 1
		    // sum - a array of int -> reduce to a single int	
		    // JS reference: numbers2.reduce((acc, curr) => acc + curr, 0)
		    // in JAVA, reduce([starting value], [lambda function with (acc, curr) params])
	    
	    int sumOfNumbers2 = numbers2.stream().reduce(0, (acc, curr) -> acc + curr);
	    System.out.println("sumOfNumbers2 " + sumOfNumbers2);
	    
	    ArrayList<String> greetings = new ArrayList<>(Arrays.asList("hi", "hello", "good morning"));
	    
	    // use case 2
		    // I want a single string where the greetings are separated by a comma
		    // a array of string -> reduce to a single string	
	    String greetingStr = greetings.stream().reduce("", (acc, curr) ->{
	    	if (acc == "") {
	    		return curr;
	    	}
	    	return acc + ", " + curr;
	    });
	    System.out.println("greetingStr: " + greetingStr);
	    
	    
	    //** reduce with 3 parameters
	    
	    // use case 3
	    	// I want a length of all the greetings
	    	// array of String reduce to an Integer
	    	// we need to use 3rd parameters of reduce - combiner
			/*
			 * a function used to combine the partial result of the reduction operation when
			 * the reduction is parallelized or when there's a mismatch between the types of
			 * the accumulator arguments and the data type of the accumulator implementation
			 */	    
	    int totalLengthOfGreetings1 = greetings.stream().reduce(
	    		0, 
	    		(acc, curr) -> acc + curr.length(), 
	    		(a,b) -> a + b);
	    
	    int totalLengthOfGreetings2 = greetings.stream().reduce(
	    		0, 
	    		(acc, curr) -> acc + curr.length(), 
	    		Integer::sum); // we 
	    
	    int totalLengthOfGreetings3 = greetings.stream().reduce(
	    		0, 
	    		(acc, curr) -> acc + curr.length(), 
	    		(a,b) -> Integer.sum(a, b));
	    
	    
	    // we can also use map to change the data type of array elements before using reduce 
	    		// -> don't need compiler
	    int totalLengthOfGreetings4 = greetings.stream()
	    		.map((g) -> g.length())
	    		.reduce(0, (acc, curr) -> acc + curr);
	    
	    
	    
	    
	    
	}
}
