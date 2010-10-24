function mhi = generate_mhi(directory_path)
% GENERATE_MHI - generates a motion history image given a directory with
% motion data
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 1
    error('MATLAB:generate_mhi:NrInputArguments', 'No input arguments were supplied. At least one is expected.');
elseif nargin <= 1
    if ~ischar(directory_path)
        error('MATLAB:generate_mhi:InvalidInputArgument', 'The first input variable must be a valid character array.');
    end
    if strcmp(directory_path, '') == 1
        error('MATLAB:generate_mhi:InvalidInputArgument', 'The first input variable cannot be empty.');
    end
elseif nargin > 1
    error('MATLAB:generate_mhi:TooManyInputArguments', 'Too many input arguments were supplied. The maximum permitted is one.');
end

files = dir([directory_path '\*.jpg']);

for i = 1:length(files)
    image = get_binary_image([directory_path '\' files(i).name]);

    if (i == 1) 
        mhi = zeros(size(image, 1), size(image, 2));
    end
    
    for x = 1:size(image, 1)
        for y = 1:size(image, 2)
            if image(x, y) == 1
                mhi(x, y) = i / length(files);
            end;
        end
    end
end