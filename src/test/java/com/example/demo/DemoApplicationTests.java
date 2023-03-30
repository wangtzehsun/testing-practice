package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DemoApplicationTests {

	private final Calculator subject = new Calculator();

	@Test
	void itShouldAddTwoNumbers() {
		//given
		int num1 = 20;
		int num2 = 30;
		//when
		Integer result = subject.add.apply(20, 30);
		//then
		int expected = 50;
		assertThat(result).isEqualTo(expected);
	}

	class Calculator{
		BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
	}

}
