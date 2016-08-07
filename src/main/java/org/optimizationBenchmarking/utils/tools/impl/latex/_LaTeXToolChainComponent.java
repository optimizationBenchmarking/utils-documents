package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessExecutor;

/** A component of a tool chain */
abstract class _LaTeXToolChainComponent implements ITextable {

  /** create the tool chain component */
  _LaTeXToolChainComponent() {
    super();
  }

  /**
   * what kind of file does this component produce?
   *
   * @return the kind of file that the component produces
   */
  abstract ELaTeXFileType _produces();

  /**
   * Can this component be used?
   *
   * @return {@code true} if and only if the component can be used
   */
  abstract boolean _canUse();

  /**
   * use this component
   *
   * @param job
   *          the main job
   * @throws IOException
   *           if I/O fails
   */
  abstract void _use(final _LaTeXMainJob job) throws IOException;

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    final String string;
    string = this.getClass().getSimpleName();
    textOut.append(string, 0, string.length());
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput mto;
    mto = new MemoryTextOutput();
    this.toText(mto);
    return mto.toString();
  }

  /**
   * require a path from the job
   *
   * @param job
   *          the job
   * @param type
   *          the type
   * @param mustExist
   *          must the file exist?
   * @param message
   *          a potential additional message
   * @return the path
   */
  final Path _getFile(final _LaTeXMainJob job, final ELaTeXFileType type,
      final boolean mustExist, final String message) {
    final Logger logger;
    final Path path;

    if (type == null) {
      throw new IllegalArgumentException("Cannot require null type."); //$NON-NLS-1$
    }

    path = job._getFile(type);
    if (path == null) {
      logger = job._getLogger();
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(
            TextUtils.className(this) + " required file of type " + //$NON-NLS-1$
                type + " but it was specified as null for job '" + //$NON-NLS-1$
                job._getFile(ELaTeXFileType.TEX) + '\'' + '.'
                + ((message != null) ? message//
                    : " This should never happen."));//$NON-NLS-1$
      }
      return null;
    }

    if (mustExist) {
      if (Files.exists(path)) {
        return path;
      }

      logger = job._getLogger();
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(this.getClass().getSimpleName()
            + " required that file of type " + //$NON-NLS-1$
            type + " should exist, but '" + path//$NON-NLS-1$
            + "' does not exist for job '" + //$NON-NLS-1$
            job._getFile(ELaTeXFileType.TEX) + '\'' + '.'
            + ((message != null) ? message//
                : " This should never happen."));//$NON-NLS-1$
      }

      return null;
    }

    return path;
  }

  /**
   * Check whether two file types equal
   *
   * @param type1
   *          the first type
   * @param type2
   *          the second type
   * @return {@code true} if the two equal, {@code false} otherwise
   */
  static final boolean _equals(final IFileType type1,
      final IFileType type2) {
    String string1, string2;

    if (type1 == null) {
      return (type2 == null);
    }
    if (type2 == null) {
      return false;
    }
    if (type1.equals(type2) || type2.equals(type1)) {
      return true;
    }

    string1 = type1.getMIMEType();
    string2 = type2.getMIMEType();
    if (string1 == null) {
      return (string2 == null);
    }
    if (string2 == null) {
      return false;
    }
    return (string1.equalsIgnoreCase(string2));
  }

  /**
   * Try to find the command line arguments of a given process.
   *
   * @param executable
   *          the executable
   * @param helpOption
   *          the option to get help
   * @param patterns
   *          the patterns to search
   * @return the discovered arguments, will have same length as
   *         {@code patterns}
   * @throws IOException
   *           if something goes wrong
   */
  static final String[] _getArgs(final Path executable,
      final String helpOption, final String... patterns)
          throws IOException {
    final ExternalProcessBuilder builder;
    final String[] retval;
    String pattern;
    int remaining, index, patternLength, lineLength, i, j, ret;
    String line;

    if ((patterns == null) || ((remaining = patterns.length) <= 0)) {
      return EmptyUtils.EMPTY_STRINGS;
    }

    builder = ExternalProcessExecutor.getInstance().use();
    builder.setDirectory(PathUtils.getTempDir());
    builder.setExecutable(executable);
    builder.addStringArgument(helpOption);
    builder.setMergeStdOutAndStdErr(true);
    builder.setStdIn(EProcessStream.IGNORE);
    builder.setStdOut(EProcessStream.AS_STREAM);

    retval = new String[remaining];

    try (final ExternalProcess ep = builder.create()) {
      try (final InputStreamReader isr = new InputStreamReader(
          ep.getStdOut())) {
        try (final BufferedReader br = new BufferedReader(isr)) {
          mainLoop: while ((line = br.readLine()) != null) {
            if ((line = TextUtils.prepare(line)) != null) {
              patternLoop: for (index = retval.length; (--index) >= 0;) {
                if (retval[index] != null) {
                  continue;
                }

                pattern = patterns[index];

                if ((patternLength = pattern
                    .length()) >= (lineLength = line.length())) {
                  continue patternLoop;
                }

                inner: for (i = 0; i < lineLength; i++) {
                  if (line.charAt(i) != '-') {
                    break inner;
                  }
                }

                if ((i + patternLength) > lineLength) {
                  continue patternLoop;
                }

                j = (i + patternLength);
                if (pattern.equalsIgnoreCase(line.substring(i, j))) {
                  retval[index] = line.substring(0, j);
                  if ((--remaining) <= 0) {
                    break mainLoop;
                  }
                }
              }
            }
          }
        }
      }

      ret = ep.waitFor();
      if (ret != 0) {
        throw new IOException("Binary '" + //$NON-NLS-1$
            executable + "' returned " + ret + //$NON-NLS-1$
            " when asked for '" + helpOption + //$NON-NLS-1$
            '\'' + '.');
      }
    }

    return retval;
  }

  /**
   * Get the paths to visit first when trying to detect a LaTeX component's
   * installation
   *
   * @return the paths to visit first
   */
  Path[] _getVisitFirst() {
    return _LaTeXToolChainComponent._getDefaultVisitFirst();
  }

  /**
   * Get the default path to be visited first on the search for a LaTeX
   * installation
   *
   * @return the default path to be visited first on the search for a LaTeX
   *         installation
   */
  static final Path[] _getDefaultVisitFirst() {
    return __DefaultVisitFirst.PATHS;
  }

  /**
   * merge the paths
   *
   * @param after
   *          the paths to visit after the paths to visit before
   * @param before
   *          the before paths
   * @return the paths
   */
  static final Path[] _visitBefore(final String[] before,
      final Path[] after) {
    final Path[] res, paths;
    int i;

    paths = ((after != null) ? after
        : _LaTeXToolChainComponent._getDefaultVisitFirst());
    res = new Path[before.length + paths.length];
    for (i = 0; i < before.length; i++) {
      res[i] = Paths.get(before[i]);
    }
    System.arraycopy(paths, 0, res, i, paths.length);
    return paths;
  }

  /** The holder for path elements to visit first */
  private static final class __DefaultVisitFirst {
    /**
     * some path elements to visit first, in order to speed up the search
     */
    static final Path[] PATHS = {
        Paths.get("C:/Program Files/MiKTeX 3.0/miktex/bin"), //$NON-NLS-1$
        Paths.get("C:/Program Files/MiKTeX 2.9/miktex/bin"), //$NON-NLS-1$
        Paths.get("C:/Program Files/MiKTeX 2.8/miktex/bin"), //$NON-NLS-1$
        Paths.get("C:/Program Files/MiKTeX 2.7/miktex/bin"), //$NON-NLS-1$
        Paths.get("C:/Program Files (x86)/MiKTeX 3.0/miktex/bin"), //$NON-NLS-1$
        Paths.get("C:/Program Files (x86)/MiKTeX 2.9/miktex/bin"), //$NON-NLS-1$
        Paths.get("C:/Program Files (x86)/MiKTeX 2.8/miktex/bin"), //$NON-NLS-1$
        Paths.get("C:/Program Files (x86)/MiKTeX 2.7/miktex/bin"), //$NON-NLS-1$
    };
  }
}
