package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathAdd;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical add function in an export document */
final class _ExportMathAdd extends MathAdd {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathAdd(final BasicMath owner) {
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
        out.append('+');
      }
      out.append('(');
      out.append(data[i]);
      out.append(')');
    }
  }
}
