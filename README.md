# SimpleLog2File

This is just a simple file logger for your Java project. By default, it creates a logfile per day in the root folder of your project.
In your logfile you will find lines like this:
```
2022-04-03 02:40:19.693 [TRACE] [Main.main()] Value of A: 2
```
| Date/Time               | Loglevel | Class and Methodname  | Logmessage    |
|-------------------------|----------|-----------------------|---------------|
| 2022-04-03 02:40:19.693 | [TRACE]  | [Main.main()]         | Value of A: 2 | 

## Requirements
You can simply download the SimpleLog2File.jar and put it in your project libaries, and you are ready to use!

## Usage
Use the default-constuctor and you are ready to use:
```
final SimpleLog2File logger = new SimpleLog2File();
```

Now you can use your logger e.g. in your main-Method of your Main-class:
```
logger.trace("Value of A: " + a);
```

This line of code produces a new line in your logfile like this:
```
2022-04-03 02:40:19.693 [TRACE] [Main.main()] Value of A: 2
```

You can give the method a simple logmessage that shows up in you log, but you can also put any type in the result. On that result, the toString()-Method is called before writing it into the logfile. Please be sure, that a toString()-Method is defined for your own objects! 

## Constructor
You can simply use the default constructor, and you are ready to use: 
```
final SimpleLog2File logger = new SimpleLog2File();
```
There is another constructor that allows you to define your own logfolder and logfilename or just the logpath.
```
final SimpleLog2File logger = new SimpleLog2File("c:\\dev\\logs");
```
or
```
final SimpleLog2File logger = new SimpleLog2File("C:\\dev\\logs", "MySpecialLogfile.log");
```

### Example:
Given is a Person.java which holds three fields: "surname", "name", "age". Simply override the toString()-Method:
```
@Override
public String toString() {
  return "Person=[surname=" + this.surname + ", name=" + this.name + ", age=" + this.age" + "]";
}
```

Now you are able to log a message for these objects:
```
final Person person = new Person("Magic", "Marcy", "42");
logger.trace(person);
```
This will produce the following line in your logfile:
```
2022-04-03 02:40:19.693 [TRACE] [Main.main()] Person=[surname=Magic, name=Marcy, age=42]
```

You are also able to concat your own infos to such an object:
```
final Person person = new Person("Magic", "Marcy", "42");
logger.trace("MyPersonObject is: " + person);
```
That will produce:
```
2022-04-03 02:40:19.693 [TRACE] [Main.main()] MyPersonObject is: Person=[surname=Magic, name=Marcy, age=42]
```

## LogLevel
You can choose between different LogLevels, but it is only to show that in the line of your logfile.<br> 
__There is no option to log just one specific level!__
```
logger.trace();
logger.debug(;
logger.info();
logger.error();
logger.warn();
```

## Error handling
If there is a problem with your specified logfile or -folder, an IOException is thrown. The exceptions stacktrace is shown in your console - not in your logfile!
