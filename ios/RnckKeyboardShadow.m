#import "RnckKeyboardShadow.h"
#import "RnckConstants.h"

@implementation RnckKeyboardShadow

- (void)setKeys:(NSArray *)keys
{
    _keys = keys;
    
    self.width = [self getScreenSize].width;
    self.height = DEFAULT_KEY_HEIGHT * [keys count];
}

- (void)setShow:(BOOL)show
{
    _show = show;
    [self setHidden: !show];
}

- (CGSize)getScreenSize {
    return [[UIScreen mainScreen] bounds].size;
}


@end
