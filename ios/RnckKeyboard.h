#import "UIKit/UIKit.h"
#import "RCTBridge.h"

#import "RCTComponent.h"

@interface RnckKeyboard : UIView

- (id)initWithBridge:(RCTBridge *)bridge;

@property (nonatomic, copy) RCTBubblingEventBlock onKeyPress;

@property (nonatomic, strong) NSArray *keys;
@property (nonatomic, assign) bool show;


@end
