package neko.fun.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pretty {

  static String checkHexColor(String s) {

    final String pattern = "^#?([0-9a-f]{6})$";

    final Pattern regex = Pattern.compile(pattern);
    final Matcher match = regex.matcher(s);

    if (match.find()) {
      return match.group(1);
    } else {
      throw new IllegalArgumentException(String.format("invalid hex string: %s", s));
    }
  }

  public static void println(String message) {
    System.out.println(message);
  }

  public static void colorPrintln(String color, String message) {
    color = Pretty.checkHexColor(color);

    final int r = Integer.parseInt(color.substring(0, 2), 16);
    final int g = Integer.parseInt(color.substring(2, 4), 16);
    final int b = Integer.parseInt(color.substring(4, 6), 16);

    var builder = new StringBuilder("\033[38;2;");
    builder.append(String.format("%d;%d;%dm%s", r, g, b, message));
    builder.append("\033[0m\n");

    System.out.print(builder);
  }

  public static void colorPanic(String color, String message) {
    Pretty.colorPrintln(color, message);
    System.exit(1);
  }

  // public static RGB32ToHSV32(fr, fg, fb float64) (float64, float64, float64) {

  // minRGB := math.Min(fr, math.Min(fg, fb))
  // maxRGB := math.Max(fr, math.Max(fg, fb))

  // if minRGB == maxRGB {
  // return 0.0, 0.0, minRGB
  // }

  // var d float64
  // switch {
  // case fr == minRGB:
  // d = fg - fb
  // case fb == minRGB:
  // d = fr - fg
  // default:
  // d = fb - fr
  // }

  // var dh float64
  // switch {
  // case fr == minRGB:
  // dh = 3.0
  // case fb == minRGB:
  // dh = 1.0
  // default:
  // dh = 5.0
  // }

  // var h, s, v float64
  // h = (60.0 * (dh - d/(maxRGB-minRGB))) / 360.0
  // s = (maxRGB - minRGB) / maxRGB
  // v = maxRGB

  // return h, s, v
  // }

  public static final String ResetAll = "\033[0m";
  public static final String Bold = "\033[1m";
  public static final String Dim = "\033[2m";
  public static final String Underlined = "\033[4m";
  public static final String Blink = "\033[5m";
  public static final String Reverse = "\033[7m";
  public static final String Hidden = "\033[8m";

  public static final String GoogleRed100 = "f4c7c3";
  public static final String GoogleRed300 = "e67c73";
  public static final String GoogleRed500 = "db4437";
  public static final String GoogleRed700 = "c53929";
  public static final String GoogleBlue100 = "c6dafc";
  public static final String GoogleBlue300 = "7baaf7";
  public static final String GoogleBlue500 = "4285f4";
  public static final String GoogleBlue700 = "3367d6";
  public static final String GoogleGreen100 = "b7e1cd";
  public static final String GoogleGreen300 = "57bb8a";
  public static final String GoogleGreen500 = "0f9d58";
  public static final String GoogleGreen700 = "0b8043";
  public static final String GoogleYellow100 = "fce8b2";
  public static final String GoogleYellow300 = "f7cb4d";
  public static final String GoogleYellow500 = "f4b400";
  public static final String GoogleYellow700 = "f09300";
  public static final String GoogleGrey100 = "f5f5f5";
  public static final String GoogleGrey300 = "e0e0e0";
  public static final String GoogleGrey500 = "9e9e9e";
  public static final String GoogleGrey700 = "616161";

  public static final String PaperRed50 = "ffebee";
  public static final String PaperRed100 = "ffcdd2";
  public static final String PaperRed200 = "ef9a9a";
  public static final String PaperRed300 = "e57373";
  public static final String PaperRed400 = "ef5350";
  public static final String PaperRed500 = "f44336";
  public static final String PaperRed600 = "e53935";
  public static final String PaperRed700 = "d32f2f";
  public static final String PaperRed800 = "c62828";
  public static final String PaperRed900 = "b71c1c";
  public static final String PaperRedA100 = "ff8a80";
  public static final String PaperRedA200 = "ff5252";
  public static final String PaperRedA400 = "ff1744";
  public static final String PaperRedA700 = "d50000";
  public static final String PaperPink50 = "fce4ec";
  public static final String PaperPink100 = "f8bbd0";
  public static final String PaperPink200 = "f48fb1";
  public static final String PaperPink300 = "f06292";
  public static final String PaperPink400 = "ec407a";
  public static final String PaperPink500 = "e91e63";
  public static final String PaperPink600 = "d81b60";
  public static final String PaperPink700 = "c2185b";
  public static final String PaperPink800 = "ad1457";
  public static final String PaperPink900 = "880e4f";
  public static final String PaperPinkA100 = "ff80ab";
  public static final String PaperPinkA200 = "ff4081";
  public static final String PaperPinkA400 = "f50057";
  public static final String PaperPinkA700 = "c51162";
  public static final String PaperPurple50 = "f3e5f5";
  public static final String PaperPurple100 = "e1bee7";
  public static final String PaperPurple200 = "ce93d8";
  public static final String PaperPurple300 = "ba68c8";
  public static final String PaperPurple400 = "ab47bc";
  public static final String PaperPurple500 = "9c27b0";
  public static final String PaperPurple600 = "8e24aa";
  public static final String PaperPurple700 = "7b1fa2";
  public static final String PaperPurple800 = "6a1b9a";
  public static final String PaperPurple900 = "4a148c";
  public static final String PaperPurpleA100 = "ea80fc";
  public static final String PaperPurpleA200 = "e040fb";
  public static final String PaperPurpleA400 = "d500f9";
  public static final String PaperPurpleA700 = "aa00ff";
  public static final String PaperDeepPurple50 = "ede7f6";
  public static final String PaperDeepPurple100 = "d1c4e9";
  public static final String PaperDeepPurple200 = "b39ddb";
  public static final String PaperDeepPurple300 = "9575cd";
  public static final String PaperDeepPurple400 = "7e57c2";
  public static final String PaperDeepPurple500 = "673ab7";
  public static final String PaperDeepPurple600 = "5e35b1";
  public static final String PaperDeepPurple700 = "512da8";
  public static final String PaperDeepPurple800 = "4527a0";
  public static final String PaperDeepPurple900 = "311b92";
  public static final String PaperDeepPurpleA100 = "b388ff";
  public static final String PaperDeepPurpleA200 = "7c4dff";
  public static final String PaperDeepPurpleA400 = "651fff";
  public static final String PaperDeepPurpleA700 = "6200ea";
  public static final String PaperIndigo50 = "e8eaf6";
  public static final String PaperIndigo100 = "c5cae9";
  public static final String PaperIndigo200 = "9fa8da";
  public static final String PaperIndigo300 = "7986cb";
  public static final String PaperIndigo400 = "5c6bc0";
  public static final String PaperIndigo500 = "3f51b5";
  public static final String PaperIndigo600 = "3949ab";
  public static final String PaperIndigo700 = "303f9f";
  public static final String PaperIndigo800 = "283593";
  public static final String PaperIndigo900 = "1a237e";
  public static final String PaperIndigoA100 = "8c9eff";
  public static final String PaperIndigoA200 = "536dfe";
  public static final String PaperIndigoA400 = "3d5afe";
  public static final String PaperIndigoA700 = "304ffe";
  public static final String PaperBlue50 = "e3f2fd";
  public static final String PaperBlue100 = "bbdefb";
  public static final String PaperBlue200 = "90caf9";
  public static final String PaperBlue300 = "64b5f6";
  public static final String PaperBlue400 = "42a5f5";
  public static final String PaperBlue500 = "2196f3";
  public static final String PaperBlue600 = "1e88e5";
  public static final String PaperBlue700 = "1976d2";
  public static final String PaperBlue800 = "1565c0";
  public static final String PaperBlue900 = "0d47a1";
  public static final String PaperBlueA100 = "82b1ff";
  public static final String PaperBlueA200 = "448aff";
  public static final String PaperBlueA400 = "2979ff";
  public static final String PaperBlueA700 = "2962ff";
  public static final String PaperLightBlue50 = "e1f5fe";
  public static final String PaperLightBlue100 = "b3e5fc";
  public static final String PaperLightBlue200 = "81d4fa";
  public static final String PaperLightBlue300 = "4fc3f7";
  public static final String PaperLightBlue400 = "29b6f6";
  public static final String PaperLightBlue500 = "03a9f4";
  public static final String PaperLightBlue600 = "039be5";
  public static final String PaperLightBlue700 = "0288d1";
  public static final String PaperLightBlue800 = "0277bd";
  public static final String PaperLightBlue900 = "01579b";
  public static final String PaperLightBlueA100 = "80d8ff";
  public static final String PaperLightBlueA200 = "40c4ff";
  public static final String PaperLightBlueA400 = "00b0ff";
  public static final String PaperLightBlueA700 = "0091ea";
  public static final String PaperCyan50 = "e0f7fa";
  public static final String PaperCyan100 = "b2ebf2";
  public static final String PaperCyan200 = "80deea";
  public static final String PaperCyan300 = "4dd0e1";
  public static final String PaperCyan400 = "26c6da";
  public static final String PaperCyan500 = "00bcd4";
  public static final String PaperCyan600 = "00acc1";
  public static final String PaperCyan700 = "0097a7";
  public static final String PaperCyan800 = "00838f";
  public static final String PaperCyan900 = "006064";
  public static final String PaperCyanA100 = "84ffff";
  public static final String PaperCyanA200 = "18ffff";
  public static final String PaperCyanA400 = "00e5ff";
  public static final String PaperCyanA700 = "00b8d4";
  public static final String PaperTeal50 = "e0f2f1";
  public static final String PaperTeal100 = "b2dfdb";
  public static final String PaperTeal200 = "80cbc4";
  public static final String PaperTeal300 = "4db6ac";
  public static final String PaperTeal400 = "26a69a";
  public static final String PaperTeal500 = "009688";
  public static final String PaperTeal600 = "00897b";
  public static final String PaperTeal700 = "00796b";
  public static final String PaperTeal800 = "00695c";
  public static final String PaperTeal900 = "004d40";
  public static final String PaperTealA100 = "a7ffeb";
  public static final String PaperTealA200 = "64ffda";
  public static final String PaperTealA400 = "1de9b6";
  public static final String PaperTealA700 = "00bfa5";
  public static final String PaperGreen50 = "e8f5e9";
  public static final String PaperGreen100 = "c8e6c9";
  public static final String PaperGreen200 = "a5d6a7";
  public static final String PaperGreen300 = "81c784";
  public static final String PaperGreen400 = "66bb6a";
  public static final String PaperGreen500 = "4caf50";
  public static final String PaperGreen600 = "43a047";
  public static final String PaperGreen700 = "388e3c";
  public static final String PaperGreen800 = "2e7d32";
  public static final String PaperGreen900 = "1b5e20";
  public static final String PaperGreenA100 = "b9f6ca";
  public static final String PaperGreenA200 = "69f0ae";
  public static final String PaperGreenA400 = "00e676";
  public static final String PaperGreenA700 = "00c853";
  public static final String PaperLightGreen50 = "f1f8e9";
  public static final String PaperLightGreen100 = "dcedc8";
  public static final String PaperLightGreen200 = "c5e1a5";
  public static final String PaperLightGreen300 = "aed581";
  public static final String PaperLightGreen400 = "9ccc65";
  public static final String PaperLightGreen500 = "8bc34a";
  public static final String PaperLightGreen600 = "7cb342";
  public static final String PaperLightGreen700 = "689f38";
  public static final String PaperLightGreen800 = "558b2f";
  public static final String PaperLightGreen900 = "33691e";
  public static final String PaperLightGreenA100 = "ccff90";
  public static final String PaperLightGreenA200 = "b2ff59";
  public static final String PaperLightGreenA400 = "76ff03";
  public static final String PaperLightGreenA700 = "64dd17";
  public static final String PaperLime50 = "f9fbe7";
  public static final String PaperLime100 = "f0f4c3";
  public static final String PaperLime200 = "e6ee9c";
  public static final String PaperLime300 = "dce775";
  public static final String PaperLime400 = "d4e157";
  public static final String PaperLime500 = "cddc39";
  public static final String PaperLime600 = "c0ca33";
  public static final String PaperLime700 = "afb42b";
  public static final String PaperLime800 = "9e9d24";
  public static final String PaperLime900 = "827717";
  public static final String PaperLimeA100 = "f4ff81";
  public static final String PaperLimeA200 = "eeff41";
  public static final String PaperLimeA400 = "c6ff00";
  public static final String PaperLimeA700 = "aeea00";
  public static final String PaperYellow50 = "fffde7";
  public static final String PaperYellow100 = "fff9c4";
  public static final String PaperYellow200 = "fff59d";
  public static final String PaperYellow300 = "fff176";
  public static final String PaperYellow400 = "ffee58";
  public static final String PaperYellow500 = "ffeb3b";
  public static final String PaperYellow600 = "fdd835";
  public static final String PaperYellow700 = "fbc02d";
  public static final String PaperYellow800 = "f9a825";
  public static final String PaperYellow900 = "f57f17";
  public static final String PaperYellowA100 = "ffff8d";
  public static final String PaperYellowA200 = "ffff00";
  public static final String PaperYellowA400 = "ffea00";
  public static final String PaperYellowA700 = "ffd600";
  public static final String PaperAmber50 = "fff8e1";
  public static final String PaperAmber100 = "ffecb3";
  public static final String PaperAmber200 = "ffe082";
  public static final String PaperAmber300 = "ffd54f";
  public static final String PaperAmber400 = "ffca28";
  public static final String PaperAmber500 = "ffc107";
  public static final String PaperAmber600 = "ffb300";
  public static final String PaperAmber700 = "ffa000";
  public static final String PaperAmber800 = "ff8f00";
  public static final String PaperAmber900 = "ff6f00";
  public static final String PaperAmberA100 = "ffe57f";
  public static final String PaperAmberA200 = "ffd740";
  public static final String PaperAmberA400 = "ffc400";
  public static final String PaperAmberA700 = "ffab00";
  public static final String PaperBrown50 = "efebe9";
  public static final String PaperBrown100 = "d7ccc8";
  public static final String PaperBrown200 = "bcaaa4";
  public static final String PaperBrown300 = "a1887f";
  public static final String PaperBrown400 = "8d6e63";
  public static final String PaperBrown500 = "795548";
  public static final String PaperBrown600 = "6d4c41";
  public static final String PaperBrown700 = "5d4037";
  public static final String PaperBrown800 = "4e342e";
  public static final String PaperBrown900 = "3e2723";
  public static final String PaperOrange50 = "fff3e0";
  public static final String PaperOrange100 = "ffe0b2";
  public static final String PaperOrange200 = "ffcc80";
  public static final String PaperOrange300 = "ffb74d";
  public static final String PaperOrange400 = "ffa726";
  public static final String PaperOrange500 = "ff9800";
  public static final String PaperOrange600 = "fb8c00";
  public static final String PaperOrange700 = "f57c00";
  public static final String PaperOrange800 = "ef6c00";
  public static final String PaperOrange900 = "e65100";
  public static final String PaperOrangeA100 = "ffd180";
  public static final String PaperOrangeA200 = "ffab40";
  public static final String PaperOrangeA400 = "ff9100";
  public static final String PaperOrangeA700 = "ff6500";
  public static final String PaperDeepOrange50 = "fbe9e7";
  public static final String PaperDeepOrange100 = "ffccbc";
  public static final String PaperDeepOrange200 = "ffab91";
  public static final String PaperDeepOrange300 = "ff8a65";
  public static final String PaperDeepOrange400 = "ff7043";
  public static final String PaperDeepOrange500 = "ff5722";
  public static final String PaperDeepOrange600 = "f4511e";
  public static final String PaperDeepOrange700 = "e64a19";
  public static final String PaperDeepOrange800 = "d84315";
  public static final String PaperDeepOrange900 = "bf360c";
  public static final String PaperDeepOrangeA100 = "ff9e80";
  public static final String PaperDeepOrangeA200 = "ff6e40";
  public static final String PaperDeepOrangeA400 = "ff3d00";
  public static final String PaperDeepOrangeA700 = "dd2c00";
  public static final String PaperGrey50 = "fafafa";
  public static final String PaperGrey100 = "f5f5f5";
  public static final String PaperGrey200 = "eeeeee";
  public static final String PaperGrey300 = "e0e0e0";
  public static final String PaperGrey400 = "bdbdbd";
  public static final String PaperGrey500 = "9e9e9e";
  public static final String PaperGrey600 = "757575";
  public static final String PaperGrey700 = "616161";
  public static final String PaperGrey800 = "424242";
  public static final String PaperGrey900 = "212121";
  public static final String PaperBlueGrey50 = "eceff1";
  public static final String PaperBlueGrey100 = "cfd8dc";
  public static final String PaperBlueGrey200 = "b0bec5";
  public static final String PaperBlueGrey300 = "90a4ae";
  public static final String PaperBlueGrey400 = "78909c";
  public static final String PaperBlueGrey500 = "607d8b";
  public static final String PaperBlueGrey600 = "546e7a";
  public static final String PaperBlueGrey700 = "455a64";
  public static final String PaperBlueGrey800 = "37474f";
  public static final String PaperBlueGrey900 = "263238";

  public static final double DarkDividerOpacity = 0.12;
  public static final double DarkDisabledOpacity = 0.38;
  public static final double DarkSecondaryOpacity = 0.54;
  public static final double DarkPrimaryOpacity = 0.87;
  public static final double LightDividerOpacity = 0.12;
  public static final double LightDisabledOpacity = 0.3;
  public static final double LightSecondaryOpacity = 0.7;
  public static final double LightPrimaryOpacity = 1.0;

}

// func checkHexColor(s string) string {
// re := regexp.MustCompile(`^#?([0-9a-f]{6})$`)

// match := re.FindAllStringSubmatch(s, -1)

// if match == nil {
// panic(fmt.Sprintf("invalid hex color string: %s", s))
// }

// return match[0][1]
// }

// func ColorCaps(color string, message string) {
// ColorPrintln(color, strings.ToUpper(message))
// }

// func ColorPrintln(color string, message string) {
// r, g, b := HexToRGB8(color)

// fmt.Printf("\033[38;2;%d;%d;%dm%s\033[0m\n", r, g, b, message)
// }

// func HexToRGB8(color string) (r, g, b uint8) {
// color = checkHexColor(color)

// ri, err := strconv.ParseUint(color[0:2], 16, 8)
// if err != nil {
// panic(err)
// }

// gi, err := strconv.ParseUint(color[2:4], 16, 8)
// if err != nil {
// panic(err)
// }

// bi, err := strconv.ParseUint(color[4:6], 16, 8)
// if err != nil {
// panic(err)
// }

// return uint8(ri), uint8(gi), uint8(bi)
// }

// func HexToHSV(color string) (h, s, v float64) {
// color = checkHexColor(color)

// r, g, b := HexToRGB8(color)

// return RGB32ToHSV32(
// float64(r)/float64(math.MaxUint8),
// float64(g)/float64(math.MaxUint8),
// float64(b)/float64(math.MaxUint8),
// )
// }

// func RGB8ToHSV32(r, g, b uint8) (float64, float64, float64) {
// var fr, fg, fb float64

// fr = float64(r) / float64(math.MaxUint8)
// fg = float64(g) / float64(math.MaxUint8)
// fb = float64(b) / float64(math.MaxUint8)

// return RGB32ToHSV32(fr, fg, fb)
// }

// func RGB32ToHSV32(fr, fg, fb float64) (float64, float64, float64) {

// minRGB := math.Min(fr, math.Min(fg, fb))
// maxRGB := math.Max(fr, math.Max(fg, fb))

// if minRGB == maxRGB {
// return 0.0, 0.0, minRGB
// }

// var d float64
// switch {
// case fr == minRGB:
// d = fg - fb
// case fb == minRGB:
// d = fr - fg
// default:
// d = fb - fr
// }

// var dh float64
// switch {
// case fr == minRGB:
// dh = 3.0
// case fb == minRGB:
// dh = 1.0
// default:
// dh = 5.0
// }

// var h, s, v float64
// h = (60.0 * (dh - d/(maxRGB-minRGB))) / 360.0
// s = (maxRGB - minRGB) / maxRGB
// v = maxRGB

// return h, s, v
// }

// func HexToColor(hc string, alpha float64) color.Color {
// r, g, b := HexToRGB8(hc)

// var a float64
// a = math.Max(0.0, alpha)
// a = math.Min(1.0, a)
// a = math.Floor(a * 255.0)

// return color.NRGBA{R: r, G: g, B: b, A: uint8(a)}
// }

// const (

// )
