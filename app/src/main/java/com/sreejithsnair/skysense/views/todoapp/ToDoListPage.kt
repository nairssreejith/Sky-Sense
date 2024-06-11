package com.sreejithsnair.skysense.views.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreejithsnair.skysense.R
import com.sreejithsnair.skysense.database.tododatabase.ToDoEntity
import com.sreejithsnair.skysense.utilities.Constants
import com.sreejithsnair.skysense.viewmodel.ToDoViewModel
import com.sreejithsnair.skysense.views.common.GenericCardContent

@Composable
fun ToDoListPage(viewModel: ToDoViewModel){

    var inputText by remember {
        mutableStateOf("")
    }

    val toDoList by viewModel.toDoList.observeAsState()
    val keyBoardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            GenericCardContent("ToDo App", Constants.taskMasterDescription)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent),
                value = inputText,
                onValueChange = {
                    inputText = it
                },
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = colorResource(id = R.color.off_white)
                ),
                textStyle = TextStyle(
                    fontFamily = Constants.customFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                ),
                label = {
                    Text(
                        text = "Enter Task",
                        fontFamily = Constants.customFontFamily,
                        color = colorResource(id = R.color.off_white)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    viewModel.addToDo(inputText)
                    inputText = ""
                    keyBoardController?.hide()
                },
                enabled = inputText.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.teal_700)  ,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = colorResource(id = R.color.off_white),
                    disabledContentColor =  Color.Gray
                )

            ) {
                Text(
                    text = "Add",
                    fontFamily = Constants.customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        toDoList?.let{
            if (it.isNotEmpty()) {
                LazyColumn(
                    content = {
                        itemsIndexed(it){
                                _, item: ToDoEntity ->
                            ToDoListCard(item = item, onDelete = {
                                viewModel.deleteToDo(item.id)
                            })
                        }
                    }
                )
            } else ShowNoTaskPending()
        }?: ShowNoTaskPending()
    }
}

@Composable
fun ShowNoTaskPending(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "No Tasks Pending",
            fontFamily = Constants.customFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }

}
