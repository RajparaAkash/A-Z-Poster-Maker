package com.letsmake.atoz.design.custom_view;

import jp.co.cyberagent.android.gpuimage.GPUImage3x3TextureSamplingFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

public class Adjust_GPU_Filter {
    private final Adjuster<? extends GPUImageFilter> adjuster;

    private class Adjust_Pixelation extends Adjuster<GPUImagePixelationFilter> {
        private Adjust_Pixelation() {
            super();
        }

        public void adjust(int i) {
            ((GPUImagePixelationFilter) getFilter()).setPixel(range(i, 1.0f, 100.0f));
        }
    }

    private class Adjust_Posterize extends Adjuster<GPUImagePosterizeFilter> {
        private Adjust_Posterize() {
            super();
        }

        public void adjust(int i) {
            ((GPUImagePosterizeFilter) getFilter()).setColorLevels(range(i, 1, 50));
        }
    }

    private class Adjust_LevelsMinMid extends Adjuster<GPUImageLevelsFilter> {
        private Adjust_LevelsMinMid() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageLevelsFilter) getFilter()).setMin(0.0f, range(i, 0.0f, 1.0f), 1.0f);
        }
    }

    private class Adjust_Monochrome extends Adjuster<GPUImageMonochromeFilter> {
        private Adjust_Monochrome() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageMonochromeFilter) getFilter()).setIntensity(range(i, 0.0f, 1.0f));
        }
    }

    private class Adjust_RGB extends Adjuster<GPUImageRGBFilter> {
        private Adjust_RGB() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageRGBFilter) getFilter()).setRed(range(i, 0.0f, 1.0f));
            ((GPUImageRGBFilter) getFilter()).setGreen(range(i, 0.0f, 1.0f));
            ((GPUImageRGBFilter) getFilter()).setBlue(range(i, 0.0f, 1.0f));
        }
    }

    public Adjust_GPU_Filter(GPUImageFilter gPUImageFilter) {
        if (gPUImageFilter instanceof GPUImageSharpenFilter) {
            this.adjuster = new Adjust_Sharpness().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSepiaFilter) {
            this.adjuster = new Adjust_Sepia().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageContrastFilter) {
            this.adjuster = new Adjust_Contrast().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageGammaFilter) {
            this.adjuster = new Adjust_Gamma().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageBrightnessFilter) {
            this.adjuster = new CustomBrightnessAdjuster().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSobelEdgeDetection) {
            this.adjuster = new Adjust_Sobel().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageEmbossFilter) {
            this.adjuster = new Adjust_Emboss().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImage3x3TextureSamplingFilter) {
            this.adjuster = new Adjust_GPU3x3Texture().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageHueFilter) {
            this.adjuster = new Adjust_Hue().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImagePosterizeFilter) {
            this.adjuster = new Adjust_Posterize().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImagePixelationFilter) {
            this.adjuster = new Adjust_Pixelation().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSaturationFilter) {
            this.adjuster = new Adjust_Saturation().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageExposureFilter) {
            this.adjuster = new AdjustExposure().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageHighlightShadowFilter) {
            this.adjuster = new Adjust_HighlightShadow().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageMonochromeFilter) {
            this.adjuster = new Adjust_Monochrome().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageOpacityFilter) {
            this.adjuster = new Adjust_Opacity().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageRGBFilter) {
            this.adjuster = new Adjust_RGB().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageWhiteBalanceFilter) {
            this.adjuster = new Adjust_WhiteBalance().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageVignetteFilter) {
            this.adjuster = new Adjust_Vignette().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageDissolveBlendFilter) {
            this.adjuster = new AdjustBlendDissolve().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageGaussianBlurFilter) {
            this.adjuster = new Adjust_GaussianBlur().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageCrosshatchFilter) {
            this.adjuster = new Adjust_CrosshatchBlur().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageBulgeDistortionFilter) {
            this.adjuster = new CustomBulgeDistortionAdjuster().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageGlassSphereFilter) {
            this.adjuster = new Adjust_GlassSphere().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageHazeFilter) {
            this.adjuster = new Adjust_Haze().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSphereRefractionFilter) {
            this.adjuster = new Adjust_SphereRefraction().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSwirlFilter) {
            this.adjuster = new Adjust_Swirl().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageColorBalanceFilter) {
            this.adjuster = new AdjustColorBalance().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageLevelsFilter) {
            this.adjuster = new Adjust_LevelsMinMid().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageBilateralFilter) {
            this.adjuster = new Custom_BilateralAdjuster().filter(gPUImageFilter);
        } else {
            this.adjuster = null;
        }
    }


    private class Adjust_Hue extends Adjuster<GPUImageHueFilter> {
        private Adjust_Hue() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageHueFilter) getFilter()).setHue(range(i, 0.0f, 360.0f));
        }
    }


    private class Adjust_Emboss extends Adjuster<GPUImageEmbossFilter> {
        private Adjust_Emboss() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageEmbossFilter) getFilter()).setIntensity(range(i, 0.0f, 4.0f));
        }
    }

    private class AdjustExposure extends Adjuster<GPUImageExposureFilter> {
        private AdjustExposure() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageExposureFilter) getFilter()).setExposure(range(i, -10.0f, 10.0f));
        }
    }

    private class Adjust_Contrast extends Adjuster<GPUImageContrastFilter> {
        private Adjust_Contrast() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageContrastFilter) getFilter()).setContrast(range(i, 0.0f, 2.0f));
        }
    }

    private class Adjust_CrosshatchBlur extends Adjuster<GPUImageCrosshatchFilter> {
        private Adjust_CrosshatchBlur() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageCrosshatchFilter) getFilter()).setCrossHatchSpacing(range(i, 0.0f, 0.06f));
            ((GPUImageCrosshatchFilter) getFilter()).setLineWidth(range(i, 0.0f, 0.006f));
        }
    }


    private class CustomBrightnessAdjuster extends Adjuster<GPUImageBrightnessFilter> {
        private CustomBrightnessAdjuster() {
            super();
        }

        @Override
        public void adjust(int i) {
            (getFilter()).setBrightness(range(i, -1.0f, 1.0f));
        }
    }

    private class Custom_BilateralAdjuster extends Adjuster<GPUImageBilateralFilter> {
        private Custom_BilateralAdjuster() {
            super();
        }

        @Override
        public void adjust(int i) {
            (getFilter()).setDistanceNormalizationFactor(range(i, 0.0f, 15.0f));
        }
    }


    private class CustomBulgeDistortionAdjuster extends Adjuster<GPUImageBulgeDistortionFilter> {
        private CustomBulgeDistortionAdjuster() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageBulgeDistortionFilter) getFilter()).setRadius(range(i, 0.0f, 1.0f));
            ((GPUImageBulgeDistortionFilter) getFilter()).setScale(range(i, -1.0f, 1.0f));
        }
    }

    private class AdjustColorBalance extends Adjuster<GPUImageColorBalanceFilter> {
        private AdjustColorBalance() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageColorBalanceFilter) getFilter()).setMidtones(new float[]{range(i, 0.0f, 1.0f), range(i / 2, 0.0f, 1.0f), range(i / 3, 0.0f, 1.0f)});
        }
    }

    private abstract class Adjuster<T extends GPUImageFilter> {
        private T filter;

        public abstract void adjust(int i);


        public float range(int i, float f, float f2) {
            return (((f2 - f) * ((float) i)) / 100.0f) + f;
        }

        private Adjuster() {
        }

        public Adjuster<T> filter(GPUImageFilter gPUImageFilter) {
            this.filter = (T) gPUImageFilter;
            return this;
        }

        public T getFilter() {
            return this.filter;
        }


        public int range(int i, int i2, int i3) {
            return (((i3 - i2) * i) / 100) + i2;
        }
    }

    private class Adjust_Vignette extends Adjuster<GPUImageVignetteFilter> {
        private Adjust_Vignette() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageVignetteFilter) getFilter()).setVignetteStart(range(i, 0.0f, 1.0f));
        }
    }

    private class Adjust_WhiteBalance extends Adjuster<GPUImageWhiteBalanceFilter> {
        private Adjust_WhiteBalance() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageWhiteBalanceFilter) getFilter()).setTemperature(range(i, 2000.0f, 8000.0f));
        }
    }


    private class Adjust_Sharpness extends Adjuster<GPUImageSharpenFilter> {
        private Adjust_Sharpness() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageSharpenFilter) getFilter()).setSharpness(range(i, -4.0f, 4.0f));
        }
    }

    private class Adjust_Sobel extends Adjuster<GPUImageSobelEdgeDetection> {
        private Adjust_Sobel() {
            super();
        }

        public void adjust(int i) {
            ((GPUImageSobelEdgeDetection) getFilter()).setLineSize(range(i, 0.0f, 5.0f));
        }
    }

    public boolean canAdjust() {
        return this.adjuster != null;
    }

    public void adjust(int i) {
        Adjuster adjuster = this.adjuster;
        if (adjuster != null) {
            adjuster.adjust(i);
        }
    }

    private class AdjustBlendDissolve extends Adjuster<GPUImageDissolveBlendFilter> {
        private AdjustBlendDissolve() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setMix(range(i, 0.0f, 1.0f));
        }
    }

    private class Adjust_GaussianBlur extends Adjuster<GPUImageGaussianBlurFilter> {
        private Adjust_GaussianBlur() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setBlurSize(range(i, 0.0f, 1.0f));
        }
    }

    private class Adjust_GlassSphere extends Adjuster<GPUImageGlassSphereFilter> {
        private Adjust_GlassSphere() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setRadius(range(i, 0.0f, 1.0f));
        }
    }

    private class Adjust_Haze extends Adjuster<GPUImageHazeFilter> {
        private Adjust_Haze() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setDistance(range(i, -0.3f, 0.3f));
            (getFilter()).setSlope(range(i, -0.3f, 0.3f));
        }
    }

    private class Adjust_HighlightShadow extends Adjuster<GPUImageHighlightShadowFilter> {
        private Adjust_HighlightShadow() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setShadows(range(i, 0.0f, 1.0f));
            (getFilter()).setHighlights(range(i, 0.0f, 1.0f));
        }
    }

    private class Adjust_GPU3x3Texture extends Adjuster<GPUImage3x3TextureSamplingFilter> {
        private Adjust_GPU3x3Texture() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setLineSize(range(i, 0.0f, 5.0f));
        }
    }

    private class Adjust_Gamma extends Adjuster<GPUImageGammaFilter> {
        private Adjust_Gamma() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setGamma(range(i, 0.0f, 3.0f));
        }
    }


    private class Adjust_Opacity extends Adjuster<GPUImageOpacityFilter> {
        private Adjust_Opacity() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setOpacity(range(i, 0.0f, 1.0f));
        }
    }


    private class Adjust_Saturation extends Adjuster<GPUImageSaturationFilter> {
        private Adjust_Saturation() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setSaturation(range(i, 0.0f, 2.0f));
        }
    }

    private class Adjust_Sepia extends Adjuster<GPUImageSepiaFilter> {
        private Adjust_Sepia() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setIntensity(range(i, 0.0f, 2.0f));
        }
    }

    private class Adjust_SphereRefraction extends Adjuster<GPUImageSphereRefractionFilter> {
        private Adjust_SphereRefraction() {
            super();
        }

        public void adjust(int i) {
            getFilter().setRadius(range(i, 0.0f, 1.0f));
        }
    }

    private class Adjust_Swirl extends Adjuster<GPUImageSwirlFilter> {
        private Adjust_Swirl() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setAngle(range(i, 0.0f, 2.0f));
        }
    }

}
