/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bop.seqAlign.framework;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;

/**
 * @author Marco Ruiz
 */
public class AlignmentUtils {
	
    public static StringBuffer cleanSequence(String buffer) throws IOException {
        int character;
        StringBuffer result = new StringBuffer();
        StringReader reader = new StringReader(buffer);
        while (true) {
            character = reader.read();
            if (character == -1) return result;
            if (character == '>') {
                for (; character != '\n' && character != -1;) character = reader.read();
            }
            if (character == -1) return result;
            if (character != '\n' && character != '\r') result.append((char)character);
        }
    }

    public static StringBuffer readSequence(String fileName) {
        String line;
        StringBuffer result = new StringBuffer();
        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(fileName, "r");
            while (file.getFilePointer() < file.length()) {
                line = file.readLine().trim();
                if (line.charAt(0) != '>') result.append(line);
            }
        } catch(Exception e){
        } finally {
        	try {
        		if (file != null) file.close();
			} catch (IOException e) {}
        }

        return result;
    }
}
