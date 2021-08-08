- Describe in your own words what is z-score and Min-Max scaling and why would we need to use such normalization methods? describe by example, if possible.
      
      The motivation behind normalization  to scale numeric data from various columns to an equivalent scale.
      for example many machine learning algorithms attempt to discover patterns in the data by comparing features 
      of data points. Nonetheless, there is an issue when the features are on definitely various scales, which solved by normalization.
      z-score and Min-Max scaling is one of the most common ways to normalize data, Min_max scaling works so that  the minimum value 
      of that feature gets transformed into a 0, the maximum value gets transformed into a 1, and every other value gets transformed 
      into a decimal between 0 and 1, and the equation to find it is 
      ( min-max normalization = (value - min)/ (max-min) ).
      z-score allows you to compare your data to a normally distributed random variable, and the equation to find it is 
      ( z-score = (rawScore - mean)/ SD ).

- We used Junit5 in our test cases, describe in your own words why would we use such library? 
what is unit testing and why would we use it?
  
  Java comes with a rich set of pre-written classes (Libraries) that a programmer can utilize while creating Java programs.
  These classes are organized into packages.
  JUnit 5  is one of the Libraries that java provide, JUnit 5 follows a modular approach, which makes expanding the API simpler. 
  It gives a separation of concern, where writing tests and discovering/running them is served from different APIs.

  Unit testing is a software testing method where “units” are tested. Developers write unit tests for their code to ensure
  that the code works effectively. This helps to detect and protect against bugs later on. Unit tests are usually written in 
  the form of functions and check the value and behavior of these functions in various scenarios.
  There are many advantages of Unit testing such as: Any bugs are found easily, Unit testing saves time,  Reusable and Reliable and 
  Unit testing improves code coverage.
  The way the task was written is called test-driven development(TDD); because the test was written before we solve and write the code.
  
  
  
  

- What is the IDE did you use?
  Intellij

- Name the Java APIs you used?
  I have not used any Java APIs.

- Mention down any difficulties or concerns you faced while working on the task. Your feedback is appreciated :D.
  Fear that the time is not enough, but by arranging the time and distributing the code into small parts,
  the process was successful.

- If you implement any of the bonus points, please mentioned it in this file
   - Provide an executable jar file.
  
- Did this task enrich technical skills or knowledge? If yes, tell us how:
      Of course, because while solving this task, I researched, learned and applied things that I did not have much knowledge about,
      such as "BigDecimal" and "unit test".
      Also, this task contains many cases, and in order to find them all and work on solving them, I practiced more debugging the 
      code, and this is good for me because I want to increase my ability to debug code.
