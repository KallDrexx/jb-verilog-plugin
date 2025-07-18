`default_nettype none

// Simple Verilog module for testing syntax highlighting
module counter (
    input wire clk,
    input wire reset,
    output reg [7:0] count
);

    // Always block for counter logic
    always @(posedge clk or posedge reset) begin
        if (reset) begin
            count <= 8'b00000000;
        end else begin
            count <= count + 1'b1;
        end
    end

    // Wire assignment example
    wire overflow = (count == 8'hFF);
    
    /* Block comment
       for testing */
    assign LED = overflow;

endmodule

// Another module
module top_module;
    wire clk, reset;
    wire [7:0] counter_out;
    
    counter my_counter (
        .clk(clk),
        .reset(reset),
        .count(counter_out)
    );
    
endmodule