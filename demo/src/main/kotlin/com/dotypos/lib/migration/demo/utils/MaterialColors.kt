package com.dotypos.lib.migration.demo.utils

object MaterialColors {
    val allPalettes: List<Palette> = listOf(
        Red,
        Pink,
        Purple,
        DeepPurple,
        Indigo,
        Blue,
        LightBlue,
        Cyan,
        Teal,
        Green,
        LightGreen,
        Lime,
        Yellow,
        Amber,
        Orange,
        DeepOrange,
        Brown,
        Grey,
        BlueGrey
    )

    object Red : BasePalette(
        color50 = "#FFEBEE",
        color100 = "#FFCDD2",
        color200 = "#EF9A9A",
        color300 = "#E57373",
        color400 = "#EF5350",
        color500 = "#F44336",
        color600 = "#E53935",
        color700 = "#D32F2F",
        color800 = "#C62828",
        color900 = "#B71C1C",
        accent100 = "#FF8A80",
        accent200 = "#FF5252",
        accent400 = "#FF1744",
        accent700 = "#D50000",
    )

    object Pink : BasePalette(
        color50 = "#FCE4EC",
        color100 = "#F8BBD0",
        color200 = "#F48FB1",
        color300 = "#F06292",
        color400 = "#EC407A",
        color500 = "#E91E63",
        color600 = "#D81B60",
        color700 = "#C2185B",
        color800 = "#AD1457",
        color900 = "#880E4F",
        accent100 = "#FF80AB",
        accent200 = "#FF4081",
        accent400 = "#F50057",
        accent700 = "#C51162",
    )

    object Purple : BasePalette(
        color50 = "#F3E5F5",
        color100 = "#E1BEE7",
        color200 = "#CE93D8",
        color300 = "#BA68C8",
        color400 = "#AB47BC",
        color500 = "#9C27B0",
        color600 = "#8E24AA",
        color700 = "#7B1FA2",
        color800 = "#6A1B9A",
        color900 = "#4A148C",
        accent100 = "#EA80FC",
        accent200 = "#E040FB",
        accent400 = "#D500F9",
        accent700 = "#AA00FF",
    )

    object DeepPurple : BasePalette(
        color50 = "#EDE7F6",
        color100 = "#D1C4E9",
        color200 = "#B39DDB",
        color300 = "#9575CD",
        color400 = "#7E57C2",
        color500 = "#673AB7",
        color600 = "#5E35B1",
        color700 = "#512DA8",
        color800 = "#4527A0",
        color900 = "#311B92",
        accent100 = "#B388FF",
        accent200 = "#7C4DFF",
        accent400 = "#651FFF",
        accent700 = "#6200EA",
    )

    object Indigo : BasePalette(
        color50 = "#E8EAF6",
        color100 = "#C5CAE9",
        color200 = "#9FA8DA",
        color300 = "#7986CB",
        color400 = "#5C6BC0",
        color500 = "#3F51B5",
        color600 = "#3949AB",
        color700 = "#303F9F",
        color800 = "#283593",
        color900 = "#1A237E",
        accent100 = "#8C9EFF",
        accent200 = "#536DFE",
        accent400 = "#3D5AFE",
        accent700 = "#304FFE",
    )

    object Blue : BasePalette(
        color50 = "#E3F2FD",
        color100 = "#BBDEFB",
        color200 = "#90CAF9",
        color300 = "#64B5F6",
        color400 = "#42A5F5",
        color500 = "#2196F3",
        color600 = "#1E88E5",
        color700 = "#1976D2",
        color800 = "#1565C0",
        color900 = "#0D47A1",
        accent100 = "#82B1FF",
        accent200 = "#448AFF",
        accent400 = "#2979FF",
        accent700 = "#2962FF",
    )

    object LightBlue : BasePalette(
        color50 = "#E1F5FE",
        color100 = "#B3E5FC",
        color200 = "#81D4FA",
        color300 = "#4FC3F7",
        color400 = "#29B6F6",
        color500 = "#03A9F4",
        color600 = "#039BE5",
        color700 = "#0288D1",
        color800 = "#0277BD",
        color900 = "#01579B",
        accent100 = "#80D8FF",
        accent200 = "#40C4FF",
        accent400 = "#00B0FF",
        accent700 = "#0091EA",
    )

    object Cyan : BasePalette(
        color50 = "#E0F7FA",
        color100 = "#B2EBF2",
        color200 = "#80DEEA",
        color300 = "#4DD0E1",
        color400 = "#26C6DA",
        color500 = "#00BCD4",
        color600 = "#00ACC1",
        color700 = "#0097A7",
        color800 = "#00838F",
        color900 = "#006064",
        accent100 = "#84FFFF",
        accent200 = "#18FFFF",
        accent400 = "#00E5FF",
        accent700 = "#00B8D4",
    )

    object Teal : BasePalette(
        color50 = "#E0F2F1",
        color100 = "#B2DFDB",
        color200 = "#80CBC4",
        color300 = "#4DB6AC",
        color400 = "#26A69A",
        color500 = "#009688",
        color600 = "#00897B",
        color700 = "#00796B",
        color800 = "#00695C",
        color900 = "#004D40",
        accent100 = "#A7FFEB",
        accent200 = "#64FFDA",
        accent400 = "#1DE9B6",
        accent700 = "#00BFA5",
    )

    object Green : BasePalette(
        color50 = "#E8F5E9",
        color100 = "#C8E6C9",
        color200 = "#A5D6A7",
        color300 = "#81C784",
        color400 = "#66BB6A",
        color500 = "#4CAF50",
        color600 = "#43A047",
        color700 = "#388E3C",
        color800 = "#2E7D32",
        color900 = "#1B5E20",
        accent100 = "#B9F6CA",
        accent200 = "#69F0AE",
        accent400 = "#00E676",
        accent700 = "#00C853",
    )

    object LightGreen : BasePalette(
        color50 = "#F1F8E9",
        color100 = "#DCEDC8",
        color200 = "#C5E1A5",
        color300 = "#AED581",
        color400 = "#9CCC65",
        color500 = "#8BC34A",
        color600 = "#7CB342",
        color700 = "#689F38",
        color800 = "#558B2F",
        color900 = "#33691E",
        accent100 = "#CCFF90",
        accent200 = "#B2FF59",
        accent400 = "#76FF03",
        accent700 = "#64DD17",
    )

    object Lime : BasePalette(
        color50 = "#F9FBE7",
        color100 = "#F0F4C3",
        color200 = "#E6EE9C",
        color300 = "#DCE775",
        color400 = "#D4E157",
        color500 = "#CDDC39",
        color600 = "#C0CA33",
        color700 = "#AFB42B",
        color800 = "#9E9D24",
        color900 = "#827717",
        accent100 = "#F4FF81",
        accent200 = "#EEFF41",
        accent400 = "#C6FF00",
        accent700 = "#AEEA00",
    )

    object Yellow : BasePalette(
        color50 = "#FFFDE7",
        color100 = "#FFF9C4",
        color200 = "#FFF59D",
        color300 = "#FFF176",
        color400 = "#FFEE58",
        color500 = "#FFEB3B",
        color600 = "#FDD835",
        color700 = "#FBC02D",
        color800 = "#F9A825",
        color900 = "#F57F17",
        accent100 = "#FFFF8D",
        accent200 = "#FFFF00",
        accent400 = "#FFEA00",
        accent700 = "#FFD600",
    )

    object Amber : BasePalette(
        color50 = "#FFF8E1",
        color100 = "#FFECB3",
        color200 = "#FFE082",
        color300 = "#FFD54F",
        color400 = "#FFCA28",
        color500 = "#FFC107",
        color600 = "#FFB300",
        color700 = "#FFA000",
        color800 = "#FF8F00",
        color900 = "#FF6F00",
        accent100 = "#FFE57F",
        accent200 = "#FFD740",
        accent400 = "#FFC400",
        accent700 = "#FFAB00",
    )

    object Orange : BasePalette(
        color50 = "#FFF3E0",
        color100 = "#FFE0B2",
        color200 = "#FFCC80",
        color300 = "#FFB74D",
        color400 = "#FFA726",
        color500 = "#FF9800",
        color600 = "#FB8C00",
        color700 = "#F57C00",
        color800 = "#EF6C00",
        color900 = "#E65100",
        accent100 = "#FFD180",
        accent200 = "#FFAB40",
        accent400 = "#FF9100",
        accent700 = "#FF6D00",
    )

    object DeepOrange : BasePalette(
        color50 = "#FBE9E7",
        color100 = "#FFCCBC",
        color200 = "#FFAB91",
        color300 = "#FF8A65",
        color400 = "#FF7043",
        color500 = "#FF5722",
        color600 = "#F4511E",
        color700 = "#E64A19",
        color800 = "#D84315",
        color900 = "#BF360C",
        accent100 = "#FF9E80",
        accent200 = "#FF6E40",
        accent400 = "#FF3D00",
        accent700 = "#DD2C00",
    )

    object Brown : ColorPalette {
        override val color50 = "#EFEBE9"
        override val color100 = "#D7CCC8"
        override val color200 = "#BCAAA4"
        override val color300 = "#A1887F"
        override val color400 = "#8D6E63"
        override val color500 = "#795548"
        override val color600 = "#6D4C41"
        override val color700 = "#5D4037"
        override val color800 = "#4E342E"
        override val color900 = "#3E2723"
        override val all = allColors
    }

    object Grey : ColorPalette {
        override val color50 = "#FAFAFA"
        override val color100 = "#F5F5F5"
        override val color200 = "#EEEEEE"
        override val color300 = "#E0E0E0"
        override val color400 = "#BDBDBD"
        override val color500 = "#9E9E9E"
        override val color600 = "#757575"
        override val color700 = "#616161"
        override val color800 = "#424242"
        override val color900 = "#212121"
        override val all = allColors
    }

    object BlueGrey : ColorPalette {
        override val color50 = "#607D8B"
        override val color100 = "#CFD8DC"
        override val color200 = "#B0BEC5"
        override val color300 = "#90A4AE"
        override val color400 = "#78909C"
        override val color500 = "#607D8B"
        override val color600 = "#546E7A"
        override val color700 = "#455A64"
        override val color800 = "#37474F"
        override val color900 = "#263238"
        override val all = allColors
    }

    abstract class BasePalette(
        final override val color50: String,
        final override val color100: String,
        final override val color200: String,
        final override val color300: String,
        final override val color400: String,
        final override val color500: String,
        final override val color600: String,
        final override val color700: String,
        final override val color800: String,
        final override val color900: String,
        final override val accent100: String,
        final override val accent200: String,
        final override val accent400: String,
        final override val accent700: String,
    ) : ColorPalette, AccentPalette {
        override val all
            get() = allColors + allAccent
    }

    interface AccentPalette : Palette {
        val accent100: String
        val accent200: String
        val accent400: String
        val accent700: String

        val allAccent: List<String>
            get() = listOf(accent100, accent200, accent400, accent700)
    }

    interface ColorPalette : Palette {
        val color50: String
        val color100: String
        val color200: String
        val color300: String
        val color400: String
        val color500: String
        val color600: String
        val color700: String
        val color800: String
        val color900: String

        val allColors: List<String>
            get() = listOf(
                color50,
                color100,
                color200,
                color300,
                color400,
                color500,
                color600,
                color700,
                color800,
                color900
            )
    }

    interface Palette {
        val all: List<String>
    }
}