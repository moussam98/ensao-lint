package com.ensao.gi5.lint.util;

import com.ensao.gi5.lint.rules.violations.Violation;
import com.ensao.gi5.lint.wrapper.CompilationUnitWrapper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Optional;

public class Utils {
     Utils() {
        throw new IllegalStateException("not to be instantiated");
    }
    public static String convertFQNToSimpleClassName(String fqn) {
        final String result;
        if (fqn == null) {
            result = null;
        } else {
            int lastIndexOfDot = fqn.lastIndexOf('.');
            if (lastIndexOfDot > 0) {
                result = fqn.substring(lastIndexOfDot + 1);
            } else {
                result = fqn;
            }
        }
        return result;
    }

    public static CompilationUnitWrapper getCompilationUnit(File file) {
        try {
            JavaParser javaParser = new JavaParser();
            ParseResult<CompilationUnit> result = javaParser.parse(file);
            return result.getResult()
                    .map(cu -> {
                        CompilationUnitWrapper compilationUnitWrapper = new CompilationUnitWrapper(cu, file.getAbsolutePath());
                        compilationUnitWrapper.addProblems(result.getProblems());
                        return compilationUnitWrapper;
                    })
                    .orElse(new CompilationUnitWrapper(new CompilationUnit(), file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<File> getFilesFromDirectory(String directoryPath) {
        return getFilesFromDirectory(new File(directoryPath));
    }

    public static Collection<File> getFilesFromDirectory(File directory) {
        return FileUtils.listFiles(directory, new String[]{"java"}, true);
    }
    
    public static Violation createNewInstanceOfViolation(String Description, String filename, int line) {
    	final Violation violation = new Violation(); 
    	violation.setDescription(Description);
    	violation.setFileName(filename);
    	violation.setLine(line);
    	return violation;
    }
    
    public static int getLine(Optional<Position> position) {
    	return position.map( p -> p.line).orElse(-1); 
    }
}
