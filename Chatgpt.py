import tkinter as tk
import openai

openai.api_key = "Your_API_KEY"

start_sequence = "\nAI:"
restart_sequence = "\nHuman: "


def generate_response():
    ask = user_input.get()
    if ask == "break":
        response_output.config(state=tk.NORMAL)
        response_output.insert(tk.END, "thank you: ")
        response_output.config(state=tk.DISABLED)
    else:
        response = openai.Completion.create(
            model="text-davinci-003",
            prompt=ask,
            temperature=0.9,
            max_tokens=150,
            top_p=1,
            frequency_penalty=0,
            presence_penalty=0.6,
            stop=[" Human:", " AI:"]
        )
        response_output.config(state=tk.NORMAL)
        response_output.insert(tk.END, f"{start_sequence}{response['choices'][0]['text']}{restart_sequence}")
        response_output.config(state=tk.DISABLED)


root = tk.Tk()
root.title("OpenAI Chatbot")

# User input
user_input_label = tk.Label(root, text="Enter your question:")
user_input_label.pack()
user_input = tk.Entry(root)
user_input.pack()

# Generate button
generate_button = tk.Button(root, text="Generate response", command=generate_response)
generate_button.pack()

# Response output
response_output_label = tk.Label(root, text="AI's response:")
response_output_label.pack()
response_output = tk.Text(root, state=tk.DISABLED)
response_output.pack()

root.mainloop()
