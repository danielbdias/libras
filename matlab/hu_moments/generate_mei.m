%% generate the MEI from a directory
function mei = generate_mei(directory_path)
% GENERATE_MEI - generates a motion energy image given a directory with
% motion data
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 1
    error('MATLAB:generate_mei:NrInputArguments', 'No input arguments were supplied. At least one is expected.');
elseif nargin <= 1
    if ~ischar(directory_path)
        error('MATLAB:generate_mei:InvalidInputArgument', 'The first input variable must be a valid character array.');
    end
    if strcmp(directory_path, '') == 1
        error('MATLAB:generate_mei:InvalidInputArgument', 'The first input variable cannot be empty.');
    end
elseif nargin > 1
    error('MATLAB:generate_mei:TooManyInputArguments', 'Too many input arguments were supplied. The maximum permitted is one.');
end

files = dir([directory_path '\*.jpg']);

for i = 1:length(files)
    image = get_binary_image([directory_path '\' files(i).name]);
    
    if (i == 1) 
        mei = zeros(size(image, 1), size(image, 2));
    end
    
    mei = merge_binary_images(mei, image);
end