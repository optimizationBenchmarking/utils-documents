package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLd;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical ld function in an export document */
final class _ExportMathLd extends MathLd {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathLd(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append("ld("); //$NON-NLS-1$
    out.append(data[0]);
    out.append(')');
  }
}
