package com.ensao.gi5.lint.visitor;

import java.util.Set;

import com.ensao.gi5.lint.util.Utils;
import com.ensao.gi5.lint.wrapper.NameWrapper;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LocalVariableNameVisitor extends VoidVisitorAdapter<Set<NameWrapper>> {

	@Override
	public void visit(VariableDeclarator n, Set<NameWrapper> arg) {
		String name = n.getNameAsString();
		int line = Utils.getLine(n.getBegin());
		arg.add(new NameWrapper(name, line)); 
		
		super.visit(n, arg);
	}


}
