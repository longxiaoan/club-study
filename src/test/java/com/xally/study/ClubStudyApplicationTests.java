package com.xally.study;

import com.xally.study.config.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClubStudyApplicationTests {

	@Test
	public void contextLoads() {
		List<Person> list = new ArrayList<>();
		list.add(new Person("jack", 20));
		list.add(new Person("mike", 25));
		list.add(new Person("tom", 30));
		list.add(new Person("rose", 30));
		list.add(new Person("rose", 30));
		list.add(new Person("daling", 12));
		list.add(new Person("lie", 13));


		Stream<String> limit = list.stream().map(Person::getName).sorted().limit(4);
		List<String> collect = limit.collect(Collectors.toList());
		//collect.stream().forEach(System.err::println);

		List<Person> p = list.stream().filter(person -> person.getAge() == 30).collect(Collectors.toList());
		List<Person> age = list.stream().skip(2).collect(Collectors.toList());
		System.out.println("------------------------------------------------");
		age.stream().map(Person::getName).forEach(System.err::println);
		System.out.println("------------------------------------------------");
		boolean b = list.stream().anyMatch(p1 -> p1.getAge() == 31);
		boolean b1 = list.stream().allMatch(p2 -> p2.getAge() > 15);
		System.out.println("b1="+b1);
		System.out.println(b);
		Integer reduce = list.stream().map(Person::getAge).reduce(0, Integer::sum);
		int sum = list.stream().map(Person::getAge).reduce(1, (a, c) -> a * c);
		long count = list.stream().count();
		System.out.println("count="+count);
		System.out.println("sum="+sum);

		System.out.println("reduce="+reduce);
		//p.stream().map(Person::getName).forEach(System.err::println);
	}

	@Test
	public void testDate(){
		LocalDate da = LocalDate.now();
		LocalDate brith = LocalDate.of(2018, 8, 5);
		System.out.println("LocalDate:"+brith);
		LocalTime now = LocalTime.now();
		System.out.println("LocalTime"+now);
		LocalTime plus = now.plus(1, ChronoUnit.HOURS);
		LocalDate plus1 = brith.plus(100, ChronoUnit.DAYS);
		System.out.println("plus1================="+plus1);
		System.err.println(plus);
		Clock system = Clock.systemDefaultZone();
		long millis = system.millis();
		System.out.println(millis);
	}

	/**
	 * 如何让俩个线程依次执行
	 */
	@Test
	public void testJoin(){
		Thread A = new Thread(() -> printNum("A"));
		Thread B = new Thread(() -> printNum("B"));
		A.start();
		B.start();
             try {
                 Thread.sleep(15000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }

	public void printNum(String name){
		for (int i = 0; i < 4; i++) {
			try {
                               TimeUnit.SECONDS.sleep(2);
                               System.out.println(name+ i + "----");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void subTest(){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		HashMap<String, String> map = new HashMap<>();
//		map.put("aa","123");
//		map.put("bb","234");
//		list.add(map);
		System.out.println(list.toString());
	}


}
