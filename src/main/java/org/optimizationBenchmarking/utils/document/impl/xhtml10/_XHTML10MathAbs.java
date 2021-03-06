package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathAbs;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical absolute function in a XHTML document */
final class _XHTML10MathAbs extends MathAbs {
  /** the before */
  private final static char[] ABS_TD = { '<', 't', 'd', ' ', 'c', 'l', 'a',
      's', 's', '=', '"', 'm', 'a', 't', 'h', 'B', 'L', '"', '>' };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _XHTML10MathAbs(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10InlineMath.MO_TR);

    out.append(_XHTML10MathAbs.ABS_TD);
    out.append(data[0]);
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);
  }
}
