#import "RnckKeyboardManager.h"

#import "RCTBridge.h"
#import "RCTEventDispatcher.h"
#import "RnckKeyboard.h"
#import "RCTShadowView.h"
#import "UIView+React.h"
#import "RnckKeyboardShadow.h"

@implementation RnckKeyboardManager

RCT_EXPORT_MODULE();

- (UIView *)view
{
    RnckKeyboard *keyboard = [[RnckKeyboard alloc] initWithBridge: self.bridge];
    return keyboard;
}

- (RCTShadowView *)shadowView {
    RnckKeyboardShadow *shadowView = [RnckKeyboardShadow new];
    shadowView.position = CSSPositionTypeAbsolute;
    shadowView.bottom = 0;
    shadowView.left = 0;
    return shadowView;
}

RCT_EXPORT_VIEW_PROPERTY(keys, NSArray *);
RCT_EXPORT_VIEW_PROPERTY(show, BOOL);

RCT_EXPORT_SHADOW_PROPERTY(keys, NSArray *);
RCT_EXPORT_SHADOW_PROPERTY(show, BOOL);

RCT_EXPORT_VIEW_PROPERTY(onKeyPress, RCTBubblingEventBlock);

@end
