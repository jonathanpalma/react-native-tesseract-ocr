
#import "RNTesseractOcr.h"
#import "RCTLog.h"

@implementation RNTesseractOcr {
    G8Tesseract *_tesseract;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_BACKGROUND, 0);
}

- (NSDictionary *)constantsToExport
{
    return @{
              @"LANG_AFRIKAANS": @"afr",
              @"LANG_AMHARIC": @"amh",
              @"LANG_ARABIC": @"ara",
              @"LANG_ASSAMESE": @"asm",
              @"LANG_AZERBAIJANI": @"aze",
              @"LANG_BELARUSIAN": @"bel",
              @"LANG_BOSNIAN": @"bos",
              @"LANG_BULGARIAN": @"bul",
              @"LANG_CHINESE_SIMPLIFIED": @"chi_sim",
              @"LANG_CHINESE_TRADITIONAL": @"chi_tra",
              @"LANG_CROATIAN": @"hrv",
              @"LANG_CUSTOM": @"custom",
              @"LANG_DANISH": @"dan",
              @"LANG_ENGLISH": @"eng",
              @"LANG_ESTONIAN": @"est",
              @"LANG_FRENCH": @"fra",
              @"LANG_GALICIAN": @"glg",
              @"LANG_GERMAN": @"deu",
              @"LANG_HEBREW": @"heb",
              @"LANG_HUNGARIAN": @"hun",
              @"LANG_ICELANDIC": @"isl",
              @"LANG_INDONESIAN": @"ind",
              @"LANG_IRISH": @"gle",
              @"LANG_ITALIAN": @"ita",
              @"LANG_JAPANESE": @"jpn",
              @"LANG_KOREAN": @"kor",
              @"LANG_LATIN": @"lat",
              @"LANG_LITHUANIAN": @"lit",
              @"LANG_NEPALI": @"nep",
              @"LANG_NORWEGIAN": @"nor",
              @"LANG_PERSIAN": @"fas",
              @"LANG_POLISH": @"pol",
              @"LANG_PORTUGUESE": @"por",
              @"LANG_RUSSIAN": @"rus",
              @"LANG_SERBIAN": @"srp",
              @"LANG_SLOVAK": @"slk",
              @"LANG_SPANISH": @"spa",
              @"LANG_SWEDISH": @"swe",
              @"LANG_TURKISH": @"tur",
              @"LANG_UKRAINIAN": @"ukr",
              @"LANG_VIETNAMESE": @"vie"
            };
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(startOcr:(nonnull NSString*)path language:(nonnull NSString*)language response:(RCTResponseSenderBlock)callback)
{
    RCTLogInfo(@"starting Ocr");
    
    _tesseract = [[G8Tesseract alloc] initWithLanguage:language];
    _tesseract.image = [[UIImage imageWithData:[NSData dataWithContentsOfFile:path]] g8_blackAndWhite];
    
    BOOL success = _tesseract.recognize;
    NSString *recognizedText = _tesseract.recognizedText;
    
    NSArray *characterBoxes = [_tesseract recognizedBlocksByIteratorLevel:G8PageIteratorLevelSymbol];
    NSMutableArray *boxes = [[NSMutableArray alloc] initWithCapacity:characterBoxes.count];
    
    for (G8RecognizedBlock *block in characterBoxes) {
        [boxes addObject:@{
                           @"text" : block.text,
                           @"boundingBox" : @{
                                   @"x": [NSNumber numberWithFloat:block.boundingBox.origin.x],
                                   @"y": [NSNumber numberWithFloat:block.boundingBox.origin.y],
                                   @"width": [NSNumber numberWithFloat:block.boundingBox.size.width],
                                   @"height": [NSNumber numberWithFloat:block.boundingBox.size.height]
                                   },
                           @"confidence" : [NSNumber numberWithFloat:block.confidence],
                           @"level" : [NSNumber numberWithInt:block.level]
                           }];
    }
    
    
    NSDictionary *response = @{
                               @"success" : [NSNumber numberWithBool:success],
                               @"recognizedText" : recognizedText,
                               @"characterBoxes" : boxes
                               };
    
    callback(@[[NSNull null], response]);
}

@end
