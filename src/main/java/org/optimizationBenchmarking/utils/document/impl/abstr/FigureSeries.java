package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for figure series
 */
public class FigureSeries extends ComplexObject implements IFigureSeries {

  /** the state when the caption has been created */
  private static final int STATE_CAPTION_CREATED = (DocumentElement.STATE_MAX_ELEMENT
      + 1);
  /** the state before the caption */
  private static final int STATE_CAPTION_BEFORE_OPEN = (FigureSeries.STATE_CAPTION_CREATED
      + 1);
  /** the state in the caption */
  private static final int STATE_CAPTION_OPENED = (FigureSeries.STATE_CAPTION_BEFORE_OPEN
      + 1);
  /** the state after the caption */
  private static final int STATE_CAPTION_CLOSED = (FigureSeries.STATE_CAPTION_OPENED
      + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[FigureSeries.STATE_CAPTION_CLOSED + 1];

    FigureSeries.STATE_NAMES[FigureSeries.STATE_CAPTION_CREATED] = "captionCreated"; //$NON-NLS-1$
    FigureSeries.STATE_NAMES[FigureSeries.STATE_CAPTION_BEFORE_OPEN] = "captionBeforeOpen"; //$NON-NLS-1$
    FigureSeries.STATE_NAMES[FigureSeries.STATE_CAPTION_OPENED] = "captionOpened"; //$NON-NLS-1$
    FigureSeries.STATE_NAMES[FigureSeries.STATE_CAPTION_CLOSED] = "captionClosed"; //$NON-NLS-1$
  }

  /** the current sub-figure index */
  int m_curFig;

  /** the size template of this figure */
  final EFigureSize m_size;

  /** the folder to contain the sub-figures */
  final Path m_folder;

  /** the number of figures per row */
  private final int m_figuresPerRow;

  /**
   * Create a figure series
   *
   * @param owner
   *          the owning section body
   * @param index
   *          the figure index in the owning section
   * @param useLabel
   *          the label to use
   * @param size
   *          the figure size
   * @param path
   *          the path suggestion
   */
  protected FigureSeries(final SectionBody owner, final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    super(owner, useLabel, index);

    if (size == null) {
      throw new IllegalArgumentException(//
          "Figure series size must not be null."); //$NON-NLS-1$
    }

    this.m_size = size;
    this.m_folder = PathUtils.normalize(
        this.m_doc.m_basePath.resolve(BasicFigure.GRAPHICS_OFFSET).resolve(//
            PathUtils.sanitizePathComponent(//
                (path == null) ? this.m_globalID : path)));
    this.m_figuresPerRow = Math.max(1,
        this.getDocument().getFiguresPerRow(size));
  }

  /**
   * Get the number of figures per row
   *
   * @return the number of figures per row
   */
  public final int getFiguresPerRow() {
    return this.m_figuresPerRow;
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state >= FigureSeries.STATE_CAPTION_CREATED)
        && (state < FigureSeries.STATE_NAMES.length)) {
      sb.append(FigureSeries.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, sb);
    }
  }

  /**
   * Obtain the size of this figure, relative to the parent document
   *
   * @return the size of this figure
   */
  public final EFigureSize getSize() {
    return this.m_size;
  }

  /**
   * Get the owning section body
   *
   * @return the owning section body
   */
  @Override
  protected SectionBody getOwner() {
    return ((SectionBody) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final FigureSeriesCaption caption() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        FigureSeries.STATE_CAPTION_CREATED);
    return this.m_driver.createFigureSeriesCaption(this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof FigureSeriesCaption) {
      this.fsmStateAssertAndSet(FigureSeries.STATE_CAPTION_CREATED,
          FigureSeries.STATE_CAPTION_BEFORE_OPEN);
      return;
    }

    if (child instanceof SubFigure) {
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof FigureSeriesCaption) {
      this.fsmStateAssertAndSet(FigureSeries.STATE_CAPTION_BEFORE_OPEN,
          FigureSeries.STATE_CAPTION_OPENED);
      return;
    }

    if (child instanceof SubFigure) {
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof FigureSeriesCaption) {
      this.fsmStateAssertAndSet(FigureSeries.STATE_CAPTION_OPENED,
          FigureSeries.STATE_CAPTION_CLOSED);
      return;
    }

    if (child instanceof SubFigure) {
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(FigureSeries.STATE_CAPTION_CLOSED,
        DocumentElement.STATE_DEAD);
    if (this.m_curFig <= 0) {
      throw new IllegalStateException(//
          "A figure series must have at least one sub-figure."); //$NON-NLS-1$
    }
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final SubFigure figure(final ILabel useLabel,
      final String path) {
    this.fsmStateAssert(FigureSeries.STATE_CAPTION_CLOSED);
    this.m_curFig++;
    return this.m_driver.createSubFigure(this, useLabel, path);
  }

  /** {@inheritDoc} */
  @Override
  final ELabelType _labelType() {
    return ELabelType.FIGURE;
  }

  /**
   * Get the (current) number of sub-figures
   *
   * @return the (current) number of sub-figures
   */
  public final int getFigureCount() {
    return this.m_curFig;
  }

}
