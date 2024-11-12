@import dto.UsersPage
@param UsersPage page

@template.layout.page(
content = @`
    <main class="flex-grow-1">
        <section>
            <div class="container-lg mt-5">
                @if(page.getUsers().isEmpty())
                    <p>Пока не добавлено ни одного пользователя</p>
                @endif
                <table class="table table-bordered table-hover mt-3">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Имя</th>
                    </tr>
                    </thead>
                    @for(var user : page.getUsers())
                        <tbody>
                        <tr>
                            <th scope="row">${page.getUsers().indexOf(user) + 1}</th>
                            <td><a href="users/"${user.getId()}>${user.getName()}</a></td>
                        </tr>
                        </tbody>
                    @endfor
                </table>
            </div>
        </section>
    </main>
`
)