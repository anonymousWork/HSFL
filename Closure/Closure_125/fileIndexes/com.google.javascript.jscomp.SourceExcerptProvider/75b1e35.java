/*
 * Copyright 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.javascript.jscomp;

/**
 * A source excerpt provider is responsible for building source code excerpt
 * of specific locations, such as a specific line or a region around a
 * given line number.
 *
 *
 */
public interface SourceExcerptProvider {
  /**
   * Source excerpt variety.
   */
  enum SourceExcerpt {
    /**
     * Line excerpt.
     */
    LINE {
      @Override
      public String get(SourceExcerptProvider source, String sourceName,
          int lineNumber, ExcerptFormatter formatter) {
        return formatter.formatLine(
            source.getSourceLine(sourceName, lineNumber), lineNumber);
      }
    },
    /**
     * Region excerpt.
     */
    REGION {
      @Override
      public String get(SourceExcerptProvider source, String sourceName,
          int lineNumber, ExcerptFormatter formatter) {
        return formatter.formatRegion(
            source.getSourceRegion(sourceName, lineNumber));
      }
    };

    /**
     * Get a source excerpt string based on the type of the source excerpt.
     */
    public abstract String get(SourceExcerptProvider source, String sourceName,
        int lineNumber, ExcerptFormatter formatter);
  }

  /**
   * Get the line indicated by the line number. This call will return only the
   * specific line.
   *
   * @param lineNumber the line number, 1 being the first line of the file
   * @return the line indicated, or {@code null} if it does not exist
   */
  String getSourceLine(String sourceName, int lineNumber);

  /**
   * Get a region around the indicated line number. The exact definition of a
   * region is implementation specific, but it must contain the line indicated
   * by the line number. A region must not start or end by a carriage return.
   *
   * @param lineNumber the line number, 1 being the first line of the file
   * @return the region around the line number indicated, or <code>null</null>
   * if it does not exist
   */
  Region getSourceRegion(String sourceName, int lineNumber);

  /**
   * A excerpt formatter is responsible of formatting source excerpts.
   */
  interface ExcerptFormatter {
    /**
     * Format a line excerpt.
     */
    String formatLine(String line, int lineNumber);

    /**
     * Format a region excerpt.
     */
    String formatRegion(Region region);
  }
}
