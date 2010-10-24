function image = get_binary_image(image_path)
% GET_BINARY_IMAGE - given an image path, returns a matrix with the image 
% binary data
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 1
    error('MATLAB:get_binary_image:NrInputArguments', 'No input arguments were supplied. At least one is expected.');
elseif nargin <= 1
    if ~ischar(image_path)
        error('MATLAB:get_binary_image:InvalidInputArgument', 'The first input variable must be a valid character array.');
    end
    if strcmp(image_path, '') == 1
        error('MATLAB:get_binary_image:InvalidInputArgument', 'The first input variable cannot be empty.');
    end
elseif nargin > 1
    error('MATLAB:get_binary_image:TooManyInputArguments', 'Too many input arguments were supplied. The maximum permitted is one.');
end

color_image = imread(image_path);

image = zeros(size(color_image, 1), size(color_image, 2));

for i = 1:size(color_image, 1)
	for j = 1:size(color_image, 2)
        pixel = color_image(i, j, :);
        if pixel(1) ~= 255 && pixel(2) ~= 255 && pixel(3) ~= 255
            image(i, j) = 1;
        end
    end
end