package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSqr;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical sqr function in an export document */
final class _ExportMathSqr extends MathSqr {
  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathSqr(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append(data[0]);
    out.append('\u00b2');
  }
}
