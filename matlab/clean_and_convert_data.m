function cleaned_data = clean_and_convert_data(parsed_values, representation_size)
% CLEAN_AND_CONVERT_DATA - Verify the parsed values, treat all missing 
% values and convert data to numeric
%
% Check to see if no input arguments were supplied.  If this is the case,
% stop execution and output an error message to the user.
if nargin < 1
    error('MATLAB:remove_missing_values:NrInputArguments', 'No input arguments were supplied. At least one is expected.');
elseif nargin <= 2
    if nargin == 1
        representation_size = 1;
    else
        if ~isnumeric(representation_size)
            error('MATLAB:remove_missing_values:InvalidInputArgument', 'The second input variable must be a valid number.');
        end    
    end
elseif nargin > 2
    error('MATLAB:remove_missing_values:TooManyInputArguments', 'Too many input arguments were supplied.  The maximum permitted is two.');
end

cleaned_data = zeros(1, length(parsed_values));

for i = 1:length(parsed_values)
    parsed_value = parsed_values(i);
    
    if strcmp(parsed_value, '?') == 1
        last_value = '';
        next_value = '';
        
        start_value = mod(i, representation_size);
        if start_value == 0
            start_value = representation_size;
        end
        
        for col = -i+representation_size:representation_size:-start_value
            index = col*(-1);
            if strcmp(parsed_values(index), '?') == 0
                last_value = parsed_values(index);
                break;
            end
        end
        
        for col = i+representation_size:representation_size:(length(parsed_values)-1)
            if strcmp(parsed_values(col), '?') == 0
                next_value = parsed_values(col);
                break;
            end
        end
        
        if strcmp(last_value, '') == 1
            parsed_value = next_value;
        elseif strcmp(next_value, '') == 1
            parsed_value = last_value;
        else
            last_value_as_number = sscanf(char(last_value), '%e');
            next_value_as_number = sscanf(char(next_value), '%e');
            
            parsed_value_as_number = mean([last_value_as_number next_value_as_number]);
            parsed_value = num2str(parsed_value_as_number);
        end
    end
    
    cleaned_data(i) = sscanf(char(parsed_value), '%e');
end