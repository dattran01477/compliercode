package com.itcode.itcodeweb.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComplierEnum {
	
	// outputcommand 
	// -rc run only first param

	Python("python", "file.py", "", "Python", "",""), //
	Ruby("ruby", "file.rb", "", "Ruby", "",""), //
	Clojure("clojure", "file.clj", "", "Clojure", "",""), //
	Php("php", "file.php", "", "Php", "",""), //
	Nodejs("nodejs", "file.js", "", "Nodejs", "",""), //
	NodejsTest("npm run test", "test.js", "-rc", "Nodejs", "",""), //
	Scala("scala", "file.scala", "", "Scala", "",""), //
	Go("\'go run\'", "file.go", "", "Go", "",""), //
	C("\'g++ -o /usercode/a.out\' ", "file.cpp", "/usercode/a.out", "C/C++", "",""), //
	Java("javac", "file.java", "./usercode/javaRunner.sh", "Java", "","test.java"), //
	JavaTest("javac -cp /usercode/lib/junit-4.13.jar:.", "file.java", "./usercode/javaTestRunner.sh", "Java", "/usercode/test.java","test.java"), //
	VBDotNet("\'vbnc -nologo -quiet\'", "file.vb", "\'mono /usercode/file.exe\'", "VB.Net", "",""), //
	Csharp("gmcs", "file.cs", "\'mono /usercode/file.exe\'", "C#", "",""), //
	Bash("/bin/bash", "file.sh", " ", "Bash", "",""), //
	ObjectiveC("gcc ", "file.m", " /usercode/a.out", "Objective-C",
			"\' -o /usercode/a.out -I/usr/include/GNUstep -L/usr/lib/GNUstep -lobjc -lgnustep-base -Wall -fconstant-string-class=NSConstantString\'",""), //
	MYSQL("/usercode/sql_runner.sh", "file.sql", "", "MYSQL", "",""), //
	Perl("perl", "file.pl", "", "Perl", "",""), //
	Rust("\'env HOME=/opt/rust /opt/rust/.cargo/bin/rustc\'", "file.rs", "/usercode/a.out", "Rust",
			"\'-o /usercode/a.out\'","");//

	private String complierName;
	private String fileName;
	private String outputCommand;
	private String languageName;
	private String eArguments;
	private String testFileName;

}
