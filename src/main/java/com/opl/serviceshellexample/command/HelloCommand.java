package com.opl.serviceshellexample.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class HelloCommand {


    @ShellMethod(key = "hello" ,value = "hello command printing hello world in console")
    public String hello(@ShellOption(defaultValue = "World !") String arg){
        return "Hello " +arg;
    }

    @ShellMethod(key = "goodbye" ,value = "hello command printing good bye in console")
    public String goodbye(){
        return "Goodbye !";
    }

    @ShellMethod(key = "install java")
    public String installJava() {
        // You can call bash commands via ProcessBuilder
        return runCommand("sudo sh commonStat.sh");
    }

    private String runCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            process.waitFor();
            return "Executed: " + command;
        } catch (Exception e) {
            return "Error: " + e.getMessage();

        }
    }


}
