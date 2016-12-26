#import "RnckKeyboard.h"
#import "UIView+React.h"
#import "RCTUIManager.h"
#import "RCTBridge.h"
#import "RnckConstants.h"

@interface RnckKeyboard ()
@property (nonatomic, weak) RCTBridge *bridge;
@end

@implementation RnckKeyboard

- (RnckKeyboard *)initWithBridge:(RCTBridge *)bridge
{
    self = [super init];
    
    _bridge = bridge;
    _keys = [NSArray new];
    _show = NO;
    
    return [RnckKeyboard new];
}

- (void)setKeys:(NSArray *)keys
{
    _keys = keys;
    [self createKeyboard: keys];
}

- (void)setShow:(BOOL)show
{
    _show = show;
    [self setHidden: !show];
}

- (void)createKeyboard:(NSArray *)keys
{
    NSMutableArray *rows = [NSMutableArray new];

    for (id keyRow in keys){
        UIView *currRow = [self createRowOfButtons: keyRow];
        
        [self addSubview: currRow];
        [currRow setTranslatesAutoresizingMaskIntoConstraints: false];
        [rows addObject:currRow];
    }
    

    [self addConstraintsToInputView: self constraintsWithRowViews: rows];
}

- (UIButton *)createButtonWithTitle:(NSString *)title
{
    UIButton *button = [UIButton buttonWithType:UIButtonTypeSystem];
    button.backgroundColor = [UIColor colorWithRed:1.0 green:1.0 blue:1.0 alpha:1];
    [button setTitleColor:[UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:1] forState:UIControlStateNormal];
    [button setTitle:title forState:UIControlStateNormal];
    [button sizeToFit];
    [button setTranslatesAutoresizingMaskIntoConstraints:false];
    [button addTarget:self action:@selector(keyPress:) forControlEvents:UIControlEventTouchUpInside];
    
    
    return button;
}

- (void)keyPress:(id)sender
{
    UIButton *button = (UIButton *)sender;
    NSDictionary *eventData = @{
                                @"target": self.reactTag,
                                @"key": button.titleLabel.text
                                };
    if (self.onKeyPress) {
        self.onKeyPress(eventData);
    }
}

- (UIView *)createRowOfButtons:(NSArray *)buttonTitles
{
    CGFloat currWidth = self.frame.size.width;
    NSMutableArray *buttons = [NSMutableArray new];
    UIView *keyboardRowView = [[UIView new] initWithFrame:CGRectMake(0, 0, currWidth, DEFAULT_KEY_HEIGHT)];
    
    for (id buttonTitle in buttonTitles){
        UIButton *button = [self createButtonWithTitle: buttonTitle];
        [buttons addObject:button];
        [keyboardRowView addSubview:button];
    }
    
    keyboardRowView.backgroundColor = [UIColor colorWithRed:0.8 green:0.8 blue:0.8 alpha:1];
    [self addIndividualButtonConstraints: buttons forMainView:keyboardRowView];
    
    return keyboardRowView;
}

-(void)addIndividualButtonConstraints: (NSArray *)buttons forMainView: (UIView *)mainView
{
    for (int index=0; index < [buttons count]; index++){
        UIButton *button = buttons[index];
        
        NSLayoutConstraint *topConstraint = [NSLayoutConstraint constraintWithItem: button attribute: NSLayoutAttributeTop relatedBy: NSLayoutRelationEqual toItem: mainView attribute: NSLayoutAttributeTop multiplier: 1.0 constant: 1];
        
        NSLayoutConstraint *bottomConstraint = [NSLayoutConstraint constraintWithItem: button attribute: NSLayoutAttributeBottom relatedBy: NSLayoutRelationEqual toItem: mainView attribute: NSLayoutAttributeBottom multiplier: 1.0 constant: -1];
        
        NSLayoutConstraint *rightConstraint;
        
        if (index == buttons.count - 1) {
            
            rightConstraint = [NSLayoutConstraint constraintWithItem: button attribute: NSLayoutAttributeRight relatedBy: NSLayoutRelationEqual toItem: mainView attribute: NSLayoutAttributeRight multiplier: 1.0 constant: -1];
            
        } else {
            
            UIButton *nextButton = buttons[index+1];
            rightConstraint = [NSLayoutConstraint constraintWithItem: button attribute: NSLayoutAttributeRight relatedBy: NSLayoutRelationEqual toItem: nextButton attribute: NSLayoutAttributeLeft multiplier: 1.0 constant: -1];
        }
        
        
        NSLayoutConstraint *leftConstraint;
        
        if (index == 0) {
            
            leftConstraint = [NSLayoutConstraint constraintWithItem: button attribute: NSLayoutAttributeLeft relatedBy: NSLayoutRelationEqual toItem: mainView attribute: NSLayoutAttributeLeft multiplier: 1.0 constant: 1];
            
        } else {
            
            UIButton *prevtButton = buttons[index-1];
            leftConstraint = [NSLayoutConstraint constraintWithItem: button attribute: NSLayoutAttributeLeft relatedBy: NSLayoutRelationEqual toItem: prevtButton attribute: NSLayoutAttributeRight multiplier: 1.0 constant: 1];
            
            UIButton *firstButton = buttons[0];
            NSLayoutConstraint *widthConstraint = [NSLayoutConstraint constraintWithItem: firstButton attribute: NSLayoutAttributeWidth relatedBy: NSLayoutRelationEqual toItem: button attribute: NSLayoutAttributeWidth multiplier: 1.0 constant: 0];
            
            [mainView addConstraint: widthConstraint];
        }
        
        [mainView addConstraints: @[topConstraint, bottomConstraint, rightConstraint, leftConstraint]];
    }
}

- (void)addConstraintsToInputView: (UIView *)inputView constraintsWithRowViews:(NSArray *)rowViews
{
    
    for (int index=0; index< [rowViews count]; index++){
        UIView *rowView = rowViews[index];
        
        NSLayoutConstraint *rightSideConstraint = [NSLayoutConstraint constraintWithItem: rowView attribute: NSLayoutAttributeRight relatedBy: NSLayoutRelationEqual toItem: inputView attribute: NSLayoutAttributeRight multiplier: 1.0 constant: -1];
        
        NSLayoutConstraint *leftConstraint = [NSLayoutConstraint constraintWithItem: rowView attribute: NSLayoutAttributeLeft relatedBy: NSLayoutRelationEqual toItem: inputView attribute: NSLayoutAttributeLeft multiplier: 1.0 constant: 1];
        
        [inputView addConstraints: @[leftConstraint, rightSideConstraint]];
        
        NSLayoutConstraint *topConstraint;
        
        if (index == 0) {
            topConstraint = [NSLayoutConstraint constraintWithItem: rowView attribute: NSLayoutAttributeTop relatedBy: NSLayoutRelationEqual toItem: inputView attribute: NSLayoutAttributeTop multiplier: 1.0 constant: 0];
            
        } else {
            
            UIView *prevRow = rowViews[index-1];
            topConstraint = [NSLayoutConstraint constraintWithItem: rowView attribute: NSLayoutAttributeTop relatedBy: NSLayoutRelationEqual toItem: prevRow attribute: NSLayoutAttributeBottom multiplier: 1.0 constant: 0];
            
            UIView *firstRow = rowViews[0];
            NSLayoutConstraint *heightConstraint = [NSLayoutConstraint constraintWithItem: firstRow attribute: NSLayoutAttributeHeight relatedBy: NSLayoutRelationEqual toItem: rowView attribute: NSLayoutAttributeHeight multiplier: 1.0 constant: 0];
            
            [inputView addConstraint: heightConstraint];
        }
        [inputView addConstraint: topConstraint];
        
        NSLayoutConstraint *bottomConstraint;
        
        if (index == rowViews.count - 1) {
            bottomConstraint = [NSLayoutConstraint constraintWithItem: rowView attribute: NSLayoutAttributeBottom relatedBy: NSLayoutRelationEqual toItem: inputView attribute: NSLayoutAttributeBottom multiplier: 1.0 constant: 0];
            
        } else {
            
            UIView *nextRow = rowViews[index+1];
            bottomConstraint = [NSLayoutConstraint constraintWithItem: rowView attribute: NSLayoutAttributeBottom relatedBy: NSLayoutRelationEqual toItem: nextRow attribute: NSLayoutAttributeTop multiplier: 1.0 constant: 0];
        }
        
        [inputView addConstraint: bottomConstraint];
    }
    
}

@end
