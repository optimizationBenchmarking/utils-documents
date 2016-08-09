package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathMin;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical min function in a XHTML document */
final class _XHTML10MathMin extends MathMin {
  /** the operator */
  private final static char[] OP = new char[] { 'm', 'i', 'n', '{' };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _XHTML10MathMin(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    int index;

    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10InlineMath.MO_TR);

    out.append(_XHTML10InlineMath.MO_TD);
    out.append(_XHTML10MathMin.OP);
    out.append(_XHTML10Table.TD_END);

    for (index = 0; index < size; index++) {
      out.append(_XHTML10InlineMath.MO_TD);
      if (index > 0) {
        out.append(_XHTML10InlineMath.MO_TD);
        out.append(',');
        out.append(_XHTML10Table.TD_END);
      }
      out.append(data[index]);
      out.append(_XHTML10Table.TD_END);
    }

    out.append(_XHTML10InlineMath.MO_TD);
    out.append('}');
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);
  }
}
