package util;

import java.io.*;

@FunctionalInterface
public interface Http {
    String get(String url) throws IOException;
}
