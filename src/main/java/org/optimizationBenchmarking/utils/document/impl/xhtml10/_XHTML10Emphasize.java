package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Emphasize;
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontStyle;

/** an emphasize element of a section in a XHTML document */
final class _XHTML10Emphasize extends Emphasize {

  /** was a tag used */
  private boolean m_tagUsed;

  /**
   * create the emphasize element
   *
   * @param owner
   *          the owner
   */
  _XHTML10Emphasize(final ComplexText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final IFontStyle fs;

    super.onOpen();

    fs = this.getDocument().getStyles().getEmphFont();
    if (fs != null) {
      this.styleUsed(fs);
      this.m_tagUsed = true;
      this.getTextOutput().append(_XHTML10StyledText.EM_BEGIN);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    if (this.m_tagUsed) {
      this.getTextOutput().append(_XHTML10StyledText.EM_END);
    }

    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(XHTML10Driver.BR);
  }
}
