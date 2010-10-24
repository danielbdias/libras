function mei = merge_binary_images(mei, image)
% MERGE_BINARY_IMAGES - merges data from two binary images
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 2
    error('MATLAB:merge_binary_images:NrInputArguments', 'No input arguments were supplied. At least two is expected.');
elseif nargin > 2
    error('MATLAB:merge_binary_images:TooManyInputArguments', 'Too many input arguments were supplied. The maximum permitted is two.');
end

for i = 1:size(image, 1)
	for j = 1:size(image, 2)
        if image(i, j) == 1
            mei(i, j) = 1;
        end
    end
end
