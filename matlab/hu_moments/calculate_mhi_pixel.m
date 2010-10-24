function pixel_value = calculate_mhi_pixel(x, y, t, D)
% GENERATE_MHI - generates a motion history image given a directory with
% motion data
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 4
    error('MATLAB:calculate_mhi_pixel:NrInputArguments', 'No input arguments were supplied. At least four is expected.');
elseif nargin <= 4
    if ~isnumeric(x)
        error('MATLAB:generate_mhi:InvalidInputArgument', 'The first input variable must be a valid number.');
    end
    if ~isnumeric(y)
        error('MATLAB:generate_mhi:InvalidInputArgument', 'The second input variable must be a valid number.');
    end
    if ~isnumeric(t)
        error('MATLAB:generate_mhi:InvalidInputArgument', 'The third input variable must be a valid number.');
    end
elseif nargin > 4
    error('MATLAB:calculate_mhi_pixel:TooManyInputArguments', 'Too many input arguments were supplied. The maximum permitted is four.');
end

if t == 0
    pixel_value = 0;
elseif D(x, y, t) == 1
    pixel_value = t;
else
    pixel_value = max(0, calculate_mhi_pixel(x, y, t-1, D)-1);
end

pixel_value = pixel_value / size(D, 3);