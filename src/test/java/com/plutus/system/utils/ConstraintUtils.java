package com.plutus.system.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstraintUtils {

    public static boolean hasSpecifiedConstraintViolation(ConstraintViolationException e, String parameterPath) {
        List<String> nodeNamesToFind = getNodeNamesFromPath(PathImpl.createPathFromString(parameterPath));
        return e.getConstraintViolations().stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(ConstraintUtils::getNodeNamesFromPath)
                .anyMatch(nodeNames -> nodeNames.equals(nodeNamesToFind));

    }

    private static List<String> getNodeNamesFromPath(Path path) {
        return StreamSupport.stream(path.spliterator(), false)
                .map(Path.Node::getName)
                .collect(Collectors.toList());
    }
}
