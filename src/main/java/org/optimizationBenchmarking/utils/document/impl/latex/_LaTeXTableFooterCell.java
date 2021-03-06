package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterCell;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;

/** a footer cell of a table in a LaTeX document */
final class _LaTeXTableFooterCell extends TableFooterCell {
  /**
   * Create a footer cell of a table
   *
   * @param owner
   *          the owning row
   * @param cols
   *          the number of columns occupied by the cell
   * @param rows
   *          the number of rows occupied by the cell
   * @param def
   *          the cell definition
   */
  _LaTeXTableFooterCell(final _LaTeXTableFooterRow owner, final int cols,
      final int rows, final ETableCellDef[] def) {
    super(owner, cols, rows, def);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    ((LaTeXDocument) (this.getDocument()))._registerCell(//
        _LaTeXTable._beginCell(this, this.getTextOutput()));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    _LaTeXTable._endCell(this, this.getTextOutput());
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }
}
