package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessExecutor;

/** the tool which turns PS documents into PDF documents */
final class _GhostScript extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** create */
  _GhostScript() {
    super();

    Path path;
    final Logger logger;
    final Path[] visitFirst;

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config("Now trying to find GhostScript executable.");//$NON-NLS-1$
    }

    visitFirst = this._getVisitFirst();

    path = PathUtils.findFirstInPath(
        new AndPredicate<>(new FileNamePredicate(true, "gswin64c", //$NON-NLS-1$
            "gs"//$NON-NLS-1$
    ), CanExecutePredicate.INSTANCE), //
        IsFilePredicate.INSTANCE, visitFirst);
    if (path == null) {
      path = PathUtils.findFirstInPath(
          new AndPredicate<>(new FileNamePredicate(true, "gswin32c"//$NON-NLS-1$
      ), CanExecutePredicate.INSTANCE), //
          IsFilePredicate.INSTANCE, visitFirst);
      if (path == null) {
        path = PathUtils.findFirstInPath(
            new AndPredicate<>(new FileNamePredicate(true, "gswin64"//$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE), //
            IsFilePredicate.INSTANCE, visitFirst);
        if (path == null) {

          path = PathUtils.findFirstInPath(
              new AndPredicate<>(new FileNamePredicate(true, "gswin32"//$NON-NLS-1$
          ), CanExecutePredicate.INSTANCE), //
              IsFilePredicate.INSTANCE, visitFirst);
        }
      }
    }

    this.m_executable = path;

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config((this.m_executable != null) ? //
          ("GhostScript executable '" + this.m_executable + //$NON-NLS-1$
              "' found.") //$NON-NLS-1$
          : "No GhostScript executable found.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append("GhostScript"); //$NON-NLS-1$
    textOut.append('(');
    textOut.append(this.m_executable);
    textOut.append(')');
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canUse() {
    return (this.m_executable != null);
  }

  /** {@inheritDoc} */
  @Override
  final void _use(final _LaTeXMainJob job) throws IOException {
    final Logger logger;
    final Path exec, ps, pdf;
    final ExternalProcessBuilder builder;
    final int ret;

    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException(
          "No GhostScript executable detected."); //$NON-NLS-1$
    }

    logger = job._getLogger();

    if ((ps = this._getFile(job, ELaTeXFileType.PS, true,
        "This means either that LaTeX and dvips were not yet run or that the LaTeX or the dvi file contain errors." //$NON-NLS-1$
    )) == null) {
      return;
    }

    if ((pdf = this._getFile(job, ELaTeXFileType.PDF, false,
        null)) == null) {
      return;
    }

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info((("Applying GhostScript to '" + ps) + '\'') + '.'); //$NON-NLS-1$
    }

    builder = ExternalProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    builder.addStringArgument("-q"); //$NON-NLS-1$
    builder.addStringArgument("-dEmbedAllFonts=true"); //$NON-NLS-1$
    builder.addStringArgument("-dSubsetFonts=true"); //$NON-NLS-1$
    builder.addStringArgument("-dCompressFonts=true"); //$NON-NLS-1$
    builder.addStringArgument("-dOptimize=true"); //$NON-NLS-1$
    builder.addStringArgument("-dPreserveCopyPage=false"); //$NON-NLS-1$
    builder.addStringArgument("-dPreserveEPSInfo=false"); //$NON-NLS-1$
    builder.addStringArgument("-dPreserveHalftoneInfo=false"); //$NON-NLS-1$
    builder.addStringArgument("-dPreserveOPIComments=false"); //$NON-NLS-1$
    builder.addStringArgument("-dPreserveOverprintSettings=false"); //$NON-NLS-1$
    builder.addStringArgument("-dPreserveSeparation=false"); //$NON-NLS-1$
    builder.addStringArgument("-dPreserveDeviceN=false"); //$NON-NLS-1$
    builder.addStringArgument("-dMaxBitmap=2147483647"); //$NON-NLS-1$
    builder.addStringArgument("-dDownsampleMonoImages=false"); //$NON-NLS-1$
    builder.addStringArgument("-dDownsampleGrayImages=false"); //$NON-NLS-1$
    builder.addStringArgument("-dDownsampleColorImages=false"); //$NON-NLS-1$
    builder.addStringArgument("-dDetectDuplicateImages=true"); //$NON-NLS-1$
    builder.addStringArgument("-dHaveTransparency=true"); //$NON-NLS-1$
    builder.addStringArgument("-dFastWebView=false"); //$NON-NLS-1$
    builder.addStringArgument("-dNOPAUSE"); //$NON-NLS-1$
    builder.addStringArgument("-dQUIET"); //$NON-NLS-1$
    builder.addStringArgument("-dBATCH"); //$NON-NLS-1$
    builder.addStringArgument("-dSAFER"); //$NON-NLS-1$
    builder.addStringArgument("-sDEVICE=pdfwrite"); //$NON-NLS-1$
    builder.addStringArgument("-dAutoRotatePages=/PageByPage"); //$NON-NLS-1$

    builder.addStringArgument("-sOutputFile=" + //$NON-NLS-1$
        PathUtils.getPhysicalPath(pdf, false));
    builder.addStringArgument("-f"); //$NON-NLS-1$
    builder.addPathArgument(ps);
    builder.addStringArgument("-c"); //$NON-NLS-1$
    builder.addStringArgument(//
        ".setpdfwrite <</NeverEmbed [ ]>> setdistillerparams"); //$NON-NLS-1$

    builder.setLogger(logger);
    builder.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdIn(EProcessStream.IGNORE);

    try (ExternalProcess proc = builder.create()) {
      if ((ret = proc.waitFor()) != 0) {
        throw new IOException(((((((("GhostScript executable '" //$NON-NLS-1$
            + exec) + "' returned value ") + ret) + //$NON-NLS-1$
            ", which indicates an error, for ps file '") + //$NON-NLS-1$
            ps) + "' created from LaTeX file '") + //$NON-NLS-1$
            job._getFile(ELaTeXFileType.TEX)) + '.');
      }
    }

    if (this._getFile(job, ELaTeXFileType.PDF, true,
        "This could mean that GhostScript failed to generate the pdf because the postscript file contains errors." //$NON-NLS-1$
    ) != null) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying GhostScript to '" + ps) + '\'') + '.'); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final ELaTeXFileType _produces() {
    return ELaTeXFileType.PDF;
  }

  /** {@inheritDoc} */
  @Override
  final Path[] _getVisitFirst() {
    return _LaTeXToolChainComponent._visitBefore(
        new String[] { //
            "C:/Program Files/gs", //$NON-NLS-1$
            "/usr/bin/gs" }, //$NON-NLS-1$
        super._getVisitFirst());
  }

  /**
   * get the description of the ghostscript tool
   *
   * @return the instance
   */
  static final _LaTeXToolChainComponentDesc _getDescription() {
    return __GhostScriptDesc.DESC;
  }

  /** the description */
  private static final class __GhostScriptDesc
      extends _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __GhostScriptDesc();

    /** create */
    private __GhostScriptDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.PS, type);
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __GhostScriptLoader.INSTANCE;
    }

    /** the loader */
    private static final class __GhostScriptLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _GhostScript();
    }
  }
}
