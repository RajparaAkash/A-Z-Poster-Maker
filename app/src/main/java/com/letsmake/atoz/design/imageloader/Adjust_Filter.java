package com.letsmake.atoz.design.imageloader;

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

public class Adjust_Filter {
    private final Adjust_Filters<? extends GPUImageFilter> filterAdjuster;

    private class Filters_SharpnessAdjust extends Adjust_Filters<GPUImageSharpenFilter> {
        private Filters_SharpnessAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setSharpness(range(i, -4.0f, 4.0f));
        }
    }

    private class Filters_SobelAdjust extends Adjust_Filters<GPUImageSobelEdgeDetection> {
        private Filters_SobelAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setLineSize(range(i, 0.0f, 5.0f));
        }
    }

    private class Filters_SphereRefractionAdjust extends Adjust_Filters<GPUImageSphereRefractionFilter> {
        private Filters_SphereRefractionAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setRadius(range(i, 0.0f, 1.0f));
        }
    }

    private class Filters_SwirlAdjust extends Adjust_Filters<GPUImageSwirlFilter> {
        private Filters_SwirlAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setAngle(range(i, 0.0f, 2.0f));
        }
    }

    private class Filters_VignetteAdjust extends Adjust_Filters<GPUImageVignetteFilter> {
        private Filters_VignetteAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setVignetteStart(range(i, 0.0f, 1.0f));
        }
    }

    private class Filters_WhiteBalanceAdjust extends Adjust_Filters<GPUImageWhiteBalanceFilter> {
        private Filters_WhiteBalanceAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setTemperature(range(i, 2000.0f, 8000.0f));
        }
    }


    public Adjust_Filter(GPUImageFilter gPUImageFilter) {
        if (gPUImageFilter instanceof GPUImageSharpenFilter) {
            this.filterAdjuster = new Filters_SharpnessAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSepiaFilter) {
            this.filterAdjuster = new Filters_SepiaAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageContrastFilter) {
            this.filterAdjuster = new Adjust_ContrastAdjustFilters().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageGammaFilter) {
            this.filterAdjuster = new Filters_GammaAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageBrightnessFilter) {
            this.filterAdjuster = new Adjust_BrightnessAdjustFilters().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSobelEdgeDetection) {
            this.filterAdjuster = new Filters_SobelAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageEmbossFilter) {
            this.filterAdjuster = new Filters_EmbossAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImage3x3TextureSamplingFilter) {
            this.filterAdjuster = new Filters_GPU3X3TextureAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageHueFilter) {
            this.filterAdjuster = new Filters_HueAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImagePosterizeFilter) {
            this.filterAdjuster = new Filters_PosterizeAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImagePixelationFilter) {
            this.filterAdjuster = new Filters_PixelationAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSaturationFilter) {
            this.filterAdjuster = new Filters_SaturationAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageExposureFilter) {
            this.filterAdjuster = new Filters_ExposureAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageHighlightShadowFilter) {
            this.filterAdjuster = new Filters_HighlightShadowAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageMonochromeFilter) {
            this.filterAdjuster = new Filters_MonochromeAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageOpacityFilter) {
            this.filterAdjuster = new Filters_OpacityAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageRGBFilter) {
            this.filterAdjuster = new Filters_RGBAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageWhiteBalanceFilter) {
            this.filterAdjuster = new Filters_WhiteBalanceAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageVignetteFilter) {
            this.filterAdjuster = new Filters_VignetteAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageDissolveBlendFilter) {
            this.filterAdjuster = new Adjust_DissolveBlendAdjustFilters().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageGaussianBlurFilter) {
            this.filterAdjuster = new Filters_GaussianBlurAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageCrosshatchFilter) {
            this.filterAdjuster = new Adjust_CrosshatchBlurAdjustFilters().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageBulgeDistortionFilter) {
            this.filterAdjuster = new Adjust_Bulge_DistortionAdjustFilters().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageGlassSphereFilter) {
            this.filterAdjuster = new Filters_GlassSphereAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageHazeFilter) {
            this.filterAdjuster = new Filters_HazeAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSphereRefractionFilter) {
            this.filterAdjuster = new Filters_SphereRefractionAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageSwirlFilter) {
            this.filterAdjuster = new Filters_SwirlAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageColorBalanceFilter) {
            this.filterAdjuster = new Adjust_ColorBalanceAdjustFilters().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageLevelsFilter) {
            this.filterAdjuster = new Filters_LevelsMinMidAdjust().filter(gPUImageFilter);
        } else if (gPUImageFilter instanceof GPUImageBilateralFilter) {
            this.filterAdjuster = new Adjust_BilateralAdjustFilters().filter(gPUImageFilter);
        } else {
            this.filterAdjuster = null;
        }
    }

    private class Adjust_BilateralAdjustFilters extends Adjust_Filters<GPUImageBilateralFilter> {
        private Adjust_BilateralAdjustFilters() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setDistanceNormalizationFactor(range(i, 0.0f, 15.0f));
        }
    }

    private class Adjust_BrightnessAdjustFilters extends Adjust_Filters<GPUImageBrightnessFilter> {
        private Adjust_BrightnessAdjustFilters() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setBrightness(range(i, -1.0f, 1.0f));
        }
    }


    private class Adjust_DissolveBlendAdjustFilters extends Adjust_Filters<GPUImageDissolveBlendFilter> {
        private Adjust_DissolveBlendAdjustFilters() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setMix(range(i, 0.0f, 1.0f));
        }
    }

    private abstract class Adjust_Filters<T extends GPUImageFilter> {
        private T filter;

        public abstract void adjust(int i);

        public float range(int i, float f, float f2) {
            return (((f2 - f) * ((float) i)) / 100.0f) + f;
        }

        private Adjust_Filters() {
        }

        public Adjust_Filters<T> filter(GPUImageFilter gPUImageFilter) {
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

    private class Filters_EmbossAdjust extends Adjust_Filters<GPUImageEmbossFilter> {
        private Filters_EmbossAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setIntensity(range(i, 0.0f, 4.0f));
        }
    }

    private class Filters_ExposureAdjust extends Adjust_Filters<GPUImageExposureFilter> {
        private Filters_ExposureAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setExposure(range(i, -10.0f, 10.0f));
        }
    }

    private class Adjust_Bulge_DistortionAdjustFilters extends Adjust_Filters<GPUImageBulgeDistortionFilter> {
        private Adjust_Bulge_DistortionAdjustFilters() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setRadius(range(i, 0.0f, 1.0f));
            (getFilter()).setScale(range(i, -1.0f, 1.0f));
        }
    }

    private class Adjust_ColorBalanceAdjustFilters extends Adjust_Filters<GPUImageColorBalanceFilter> {
        private Adjust_ColorBalanceAdjustFilters() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setMidtones(new float[]{range(i, 0.0f, 1.0f), range(i / 2, 0.0f, 1.0f), range(i / 3, 0.0f, 1.0f)});
        }
    }

    private class Adjust_ContrastAdjustFilters extends Adjust_Filters<GPUImageContrastFilter> {
        private Adjust_ContrastAdjustFilters() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setContrast(range(i, 0.0f, 2.0f));
        }
    }

    private class Adjust_CrosshatchBlurAdjustFilters extends Adjust_Filters<GPUImageCrosshatchFilter> {
        private Adjust_CrosshatchBlurAdjustFilters() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setCrossHatchSpacing(range(i, 0.0f, 0.06f));
            (getFilter()).setLineWidth(range(i, 0.0f, 0.006f));
        }
    }


    private class Filters_HighlightShadowAdjust extends Adjust_Filters<GPUImageHighlightShadowFilter> {
        private Filters_HighlightShadowAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setShadows(range(i, 0.0f, 1.0f));
            (getFilter()).setHighlights(range(i, 0.0f, 1.0f));
        }
    }

    private class Filters_HueAdjust extends Adjust_Filters<GPUImageHueFilter> {
        private Filters_HueAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setHue(range(i, 0.0f, 360.0f));
        }
    }

    private class Filters_LevelsMinMidAdjust extends Adjust_Filters<GPUImageLevelsFilter> {
        private Filters_LevelsMinMidAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setMin(0.0f, range(i, 0.0f, 1.0f), 1.0f);
        }
    }

    private class Filters_MonochromeAdjust extends Adjust_Filters<GPUImageMonochromeFilter> {
        private Filters_MonochromeAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setIntensity(range(i, 0.0f, 1.0f));
        }
    }

    private class Filters_OpacityAdjust extends Adjust_Filters<GPUImageOpacityFilter> {
        private Filters_OpacityAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setOpacity(range(i, 0.0f, 1.0f));
        }
    }

    private class Filters_PixelationAdjust extends Adjust_Filters<GPUImagePixelationFilter> {
        private Filters_PixelationAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setPixel(range(i, 1.0f, 100.0f));
        }
    }

    private class Filters_PosterizeAdjust extends Adjust_Filters<GPUImagePosterizeFilter> {
        private Filters_PosterizeAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setColorLevels(range(i, 1, 50));
        }
    }

    private class Filters_RGBAdjust extends Adjust_Filters<GPUImageRGBFilter> {
        private Filters_RGBAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setRed(range(i, 0.0f, 1.0f));
            (getFilter()).setGreen(range(i, 0.0f, 1.0f));
            (getFilter()).setBlue(range(i, 0.0f, 1.0f));
        }
    }

    private class Filters_GPU3X3TextureAdjust extends Adjust_Filters<GPUImage3x3TextureSamplingFilter> {
        private Filters_GPU3X3TextureAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setLineSize(range(i, 0.0f, 5.0f));
        }
    }

    private class Filters_GammaAdjust extends Adjust_Filters<GPUImageGammaFilter> {
        private Filters_GammaAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setGamma(range(i, 0.0f, 3.0f));
        }
    }

    private class Filters_GaussianBlurAdjust extends Adjust_Filters<GPUImageGaussianBlurFilter> {
        private Filters_GaussianBlurAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setBlurSize(range(i, 0.0f, 1.0f));
        }
    }

    private class Filters_GlassSphereAdjust extends Adjust_Filters<GPUImageGlassSphereFilter> {
        private Filters_GlassSphereAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setRadius(range(i, 0.0f, 1.0f));
        }
    }

    private class Filters_HazeAdjust extends Adjust_Filters<GPUImageHazeFilter> {
        private Filters_HazeAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setDistance(range(i, -0.3f, 0.3f));
            (getFilter()).setSlope(range(i, -0.3f, 0.3f));
        }
    }

    private class Filters_SaturationAdjust extends Adjust_Filters<GPUImageSaturationFilter> {
        private Filters_SaturationAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setSaturation(range(i, 0.0f, 2.0f));
        }
    }

    private class Filters_SepiaAdjust extends Adjust_Filters<GPUImageSepiaFilter> {
        private Filters_SepiaAdjust() {
            super();
        }

        public void adjust(int i) {
            (getFilter()).setIntensity(range(i, 0.0f, 2.0f));
        }
    }


    public boolean canAdjust() {
        return this.filterAdjuster != null;
    }

    public void adjust(int i) {
        Adjust_Filters filterAdjuster = this.filterAdjuster;
        if (filterAdjuster != null) {
            filterAdjuster.adjust(i);
        }
    }
}
