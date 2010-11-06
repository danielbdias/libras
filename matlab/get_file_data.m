function file_data = get_file_data(file_path, representation_size)
% GET_FILE_DATA	- Extract data from a text file with libras movements.
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 1
    error('MATLAB:get_file_data:NrInputArguments', 'No input arguments were supplied. At least one is expected.');
elseif nargin <= 2
    if ~ischar(file_path)
        error('MATLAB:get_file_data:InvalidInputArgument', 'The first input variable must be a valid character array.');
    end
    if strcmp(file_path, '') == 1
        error('MATLAB:get_file_data:InvalidInputArgument', 'The first input variable cannot be empty.');
    end
    if nargin == 1
        representation_size = 1;
    end
elseif nargin > 2
    error('MATLAB:get_file_data:TooManyInputArguments', 'Too many input arguments were supplied.  The maximum permitted is two.');
end

display(['Reading data from file: ' file_path]);

fid = fopen(file_path, 'r');

if fid > 0
    
    tline = fgetl(fid);
    
    line_count = 1;
    
    while ischar(tline)
        parsed_line = regexp(tline, ',', 'split');
        cleaned_data = clean_and_convert_data(parsed_line, representation_size);
        
        for col = 1:length(parsed_line)      
            file_data(line_count, col) = cleaned_data(col);
        end
        
        line_count = line_count+1;
        tline = fgetl(fid);
    end

    status = fclose(fid);
    
    if status ~= 0
        error('MATLAB:get_file_data:UnableToCloseFile', 'The data file passed to the function cannot be closed.');    
    end
else
    error('MATLAB:get_file_data:UnableToOpenFile', 'The data file passed to the function cannot be opened.');
end

display(['File ' file_path ' read. ' num2str(line_count) ' lines obtained.']);