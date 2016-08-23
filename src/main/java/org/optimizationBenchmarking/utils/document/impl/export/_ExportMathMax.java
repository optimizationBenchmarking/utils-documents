package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathMax;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical max function in a XHTML document */
final class _ExportMathMax extends MathMax {
  /** the operator */
  private final static char[] OP = new char[] { 'm', 'a', 'x', ' ' };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathMax(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    int index;

    out.append(_ExportMathMax.OP);

    for (index = 0; index < size; index++) {
      if (index > 0) {
        out.append(',');
      }
      out.append(data[index]);
    }
  }
}
