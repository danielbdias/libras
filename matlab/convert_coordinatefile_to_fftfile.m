function convert_coordinatefile_to_fftfile(input_file_path, output_file_path)
% CONVERT_COORDINATEFILE_TO_FFTFILE	- Convert data from a text file with
% libras movements in coordinate representation to a representation using
% the fourier transform
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 2
    error('MATLAB:get_file_data:NrInputArguments', 'No input arguments were supplied. At least two is expected.');
elseif nargin <= 2
    if ~ischar(input_file_path)
        error('MATLAB:get_file_data:InvalidInputArgument', 'The first input variable must be a valid character array.');
    end
    if strcmp(input_file_path, '') == 1
        error('MATLAB:get_file_data:InvalidInputArgument', 'The first input variable cannot be empty.');
    end
    if ~ischar(output_file_path)
        error('MATLAB:get_file_data:InvalidInputArgument', 'The second input variable must be a valid character array.');
    end
    if strcmp(output_file_path, '') == 1
        error('MATLAB:get_file_data:InvalidInputArgument', 'The second input variable cannot be empty.');
    end
elseif nargin > 2
    error('MATLAB:get_file_data:TooManyInputArguments', 'Too many input arguments were supplied.  The maximum permitted is two.');
end

coordinateFileData = get_file_data(input_file_path, 2);

fftFileData = zeros(size(coordinateFileData, 1), size(coordinateFileData, 2));

for i = 1:size(coordinateFileData, 1)
    line = coordinateFileData(i,1:end-1);
    
    x = line(1:2:end);
    y = line(2:2:end);
    
    z = fft(x + 1i*y);
    
    realComponent = real(z);
    imaginaryComponent = imag(z);
    
    for j = 1:size(realComponent,2)
        fftFileData(i, (j*2)-1) = realComponent(j);
        fftFileData(i, (j*2)) = imaginaryComponent(j);    
    end
    
    fftFileData(i, end) = coordinateFileData(i,end);
end

fid = fopen(output_file_path, 'w');

if fid > 0

    for i = 1:size(fftFileData, 1)
        movementData = fftFileData(i, 1:end-1);
        classNumber = fftFileData(i, end);
        
        for j = 1:size(movementData,2)
            fprintf(fid, '%f,', movementData(j));
        end
        
        fprintf(fid, '%d\r\n', classNumber);
    end
    
    status = fclose(fid);
    
    if status ~= 0
        error('MATLAB:get_file_data:UnableToCloseFile', 'The data file passed to the function cannot be closed.');    
    end
else
    error('MATLAB:get_file_data:UnableToOpenFile', 'The data file passed to the function cannot be opened.');
end