//
//  TapActivityIndicatorViewVertexShader.vsh
//  TapGLKit/TapActivityIndicatorView
//
//  Created by Dennis Pashkov on 12/27/16.
//  Copyright © 2016 Tap Payments. All rights reserved.
//

attribute highp vec2 position;

void main(void) {
    
    gl_Position = vec4(position, 0.0, 1.0);
}
