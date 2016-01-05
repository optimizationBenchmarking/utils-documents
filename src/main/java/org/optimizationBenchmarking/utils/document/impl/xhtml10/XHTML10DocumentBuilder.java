package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentBuilder;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentDriver;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.PageDimension;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.style.impl.DefaultPalettes;
import org.optimizationBenchmarking.utils.graphics.style.impl.Styles;
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontPalette;

/**
 * The XHTML 1.0 document builder
 */
public final class XHTML10DocumentBuilder extends DocumentBuilder {

  /**
   * the XHTML 1.0 document builder
   *
   * @param driver
   *          the driver to use
   */
  XHTML10DocumentBuilder(final XHTML10Driver driver) {
    super(driver);
  }

  /** {@inheritDoc} */
  @Override
  protected XHTML10ConfigurationBuilder createConfigurationBuilder(
      final DocumentDriver owner) {
    return new XHTML10ConfigurationBuilder((XHTML10Driver) owner);
  }

  /**
   * Set the screen size for the document builder
   *
   * @param size
   *          the screen size
   * @return this document builder
   */
  public final XHTML10DocumentBuilder setScreenSize(
      final PhysicalDimension size) {
    ((XHTML10ConfigurationBuilder) (this.getConfigurationBuilder()))
        .setScreenSize(size);
    return this;
  }

  /**
   * Set the screen size of this builder
   *
   * @param size
   *          the screen size
   * @return this builder
   */
  public final XHTML10DocumentBuilder setScreenSize(
      final EScreenSize size) {
    ((XHTML10ConfigurationBuilder) (this.getConfigurationBuilder()))
        .setScreenSize(size);
    return this;
  }

  /**
   * Get the screen size
   *
   * @return the screen size
   */
  public final PageDimension getScreenSize() {
    return ((XHTML10ConfigurationBuilder) (this.getConfigurationBuilder()))
        .getScreenSize();
  }

  /**
   * Set the font palette to be used
   *
   * @param fonts
   *          the font palette to be used
   * @return this builder
   */
  public final XHTML10DocumentBuilder setFontPalette(
      final IFontPalette fonts) {
    ((XHTML10ConfigurationBuilder) (this.getConfigurationBuilder()))
        .setFontPalette(fonts);
    return this;
  }

  /**
   * Get the font palette of this document builder
   *
   * @return the font palette of this document builder
   */
  public final IFontPalette getFontPalette() {
    return ((XHTML10ConfigurationBuilder) (this.getConfigurationBuilder()))
        .getFontPalette();
  }

  /** {@inheritDoc} */
  @Override
  protected final Styles createStyles() {
    return DefaultPalettes.createDefaultStyles(//
        this.getFontPalette(), //
        this.getColorModel());
  }

  /** {@inheritDoc} */
  @Override
  protected final Document doCreateDocument() {
    return new _XHTML10Document(this);
  }

}
