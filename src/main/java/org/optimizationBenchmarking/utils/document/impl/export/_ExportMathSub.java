package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSub;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical sub function in an export document */
final class _ExportMathSub extends MathSub {
  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathSub(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    int i;
    out.append('{');
    for (i = 0; i < size; i++) {
      if (i > 0) {
        out.append('-');
      }
      out.append('(');
      out.append(data[i]);
      out.append(')');
    }
  }
}
